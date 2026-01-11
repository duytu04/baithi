const api = {
    indexes: "/api/indexes",
    playerIndex: "/api/player-index"
};

const elements = {
    form: document.getElementById("add-form"),
    playerName: document.getElementById("playerName"),
    age: document.getElementById("age"),
    indexName: document.getElementById("indexName"),
    value: document.getElementById("value"),
    resetForm: document.getElementById("resetForm"),
    rangeHint: document.getElementById("range-hint"),
    message: document.getElementById("message"),
    tableBody: document.getElementById("table-body")
};

function showMessage(text, type) {
    elements.message.textContent = text || "";
    elements.message.className = "message";
    if (type) {
        elements.message.classList.add(type);
    }
}

async function parseResponse(response) {
    if (response.ok) {
        return response.status === 204 ? null : response.json();
    }
    let errorMessage = "Request failed";
    try {
        const payload = await response.json();
        if (payload && payload.message) {
            errorMessage = payload.message;
        }
    } catch (error) {
        errorMessage = response.statusText || errorMessage;
    }
    throw new Error(errorMessage);
}

async function loadIndexes() {
    const response = await fetch(api.indexes);
    const list = await parseResponse(response);
    elements.indexName.innerHTML = "";
    list.forEach((item) => {
        const option = document.createElement("option");
        option.value = item.name;
        option.textContent = `${item.name} (${item.valueMin} - ${item.valueMax})`;
        option.dataset.min = item.valueMin;
        option.dataset.max = item.valueMax;
        elements.indexName.appendChild(option);
    });
    updateRangeHint();
}

function updateRangeHint() {
    const selected = elements.indexName.selectedOptions[0];
    if (!selected) {
        elements.rangeHint.textContent = "";
        return;
    }
    const min = selected.dataset.min;
    const max = selected.dataset.max;
    elements.rangeHint.textContent = `Giá trị hợp lệ: ${min} - ${max}`;
    elements.value.min = min;
    elements.value.max = max;
}

function buildCell(text) {
    const cell = document.createElement("td");
    cell.textContent = text;
    return cell;
}

function renderTable(list) {
    elements.tableBody.innerHTML = "";
    if (!list.length) {
        const row = document.createElement("tr");
        const cell = document.createElement("td");
        cell.colSpan = 6;
        cell.textContent = "Chưa có dữ liệu.";
        cell.className = "empty";
        row.appendChild(cell);
        elements.tableBody.appendChild(row);
        return;
    }

    list.forEach((item) => {
        const row = document.createElement("tr");
        row.appendChild(buildCell(item.playerName));
        row.appendChild(buildCell(item.age));
        row.appendChild(buildCell(item.indexName));

        const valueCell = document.createElement("td");
        const valueInput = document.createElement("input");
        valueInput.type = "number";
        valueInput.step = "any";
        valueInput.min = item.min;
        valueInput.max = item.max;
        valueInput.value = item.value;
        valueInput.id = `value-${item.id}`;
        valueInput.className = "value-input";
        valueCell.appendChild(valueInput);
        row.appendChild(valueCell);

        row.appendChild(buildCell(`${item.min} - ${item.max}`));

        const actionCell = document.createElement("td");
        const updateButton = document.createElement("button");
        updateButton.textContent = "Lưu";
        updateButton.className = "primary";
        updateButton.dataset.action = "update";
        updateButton.dataset.id = item.id;
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Xóa";
        deleteButton.className = "danger";
        deleteButton.dataset.action = "delete";
        deleteButton.dataset.id = item.id;
        actionCell.appendChild(updateButton);
        actionCell.appendChild(deleteButton);
        row.appendChild(actionCell);

        elements.tableBody.appendChild(row);
    });
}

async function loadTable() {
    const response = await fetch(api.playerIndex);
    const list = await parseResponse(response);
    renderTable(list);
}

elements.indexName.addEventListener("change", updateRangeHint);

elements.form.addEventListener("submit", async (event) => {
    event.preventDefault();
    showMessage("", "");
    const payload = {
        playerName: elements.playerName.value.trim(),
        age: Number(elements.age.value),
        indexName: elements.indexName.value,
        value: Number(elements.value.value)
    };

    try {
        const response = await fetch(api.playerIndex, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        await parseResponse(response);
        showMessage("Đã thêm chỉ số thành công.", "success");
        elements.value.value = "";
        await loadTable();
    } catch (error) {
        showMessage(error.message, "error");
    }
});

elements.resetForm.addEventListener("click", () => {
    elements.form.reset();
    updateRangeHint();
    showMessage("", "");
});

elements.tableBody.addEventListener("click", async (event) => {
    const target = event.target;
    if (!(target instanceof HTMLElement)) {
        return;
    }
    const action = target.dataset.action;
    const id = target.dataset.id;
    if (!action || !id) {
        return;
    }

    if (action === "update") {
        const input = document.getElementById(`value-${id}`);
        const value = Number(input.value);
        try {
            const response = await fetch(`${api.playerIndex}/${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ value })
            });
        await parseResponse(response);
        showMessage("Đã cập nhật chỉ số.", "success");
        await loadTable();
    } catch (error) {
        showMessage(error.message, "error");
        }
    }

    if (action === "delete") {
        if (!confirm("Xóa chỉ số này?")) {
            return;
        }
        try {
            const response = await fetch(`${api.playerIndex}/${id}`, {
                method: "DELETE"
            });
            await parseResponse(response);
            showMessage("Đã xóa chỉ số.", "success");
            await loadTable();
        } catch (error) {
            showMessage(error.message, "error");
        }
    }
});

async function init() {
    try {
        await loadIndexes();
        await loadTable();
    } catch (error) {
        showMessage(error.message, "error");
    }
}

init();
