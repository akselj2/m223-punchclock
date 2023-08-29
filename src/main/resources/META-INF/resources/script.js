const URL = 'http://localhost:8080';
let entries = [];

const dateAndTimeToDate = (dateString, timeString) => {
    return new Date(`${dateString}T${timeString}`).toISOString();
};

const createEntry = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const entry = {};
    entry['checkIn'] = dateAndTimeToDate(formData.get('checkInDate'), formData.get('checkInTime'));
    entry['checkOut'] = dateAndTimeToDate(formData.get('checkOutDate'), formData.get('checkOutTime'));
    entry['category'] = formData.get('category');


    fetch(`${URL}/entries`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(entry)
    }).then((result) => {
        result.json().then((entry) => {
            entries.push(entry);
            renderEntries();
        });
    });
};

const indexEntries = () => {
    fetch(`${URL}/entries`, {
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            entries = result;
            renderEntries();
        });
    });
    renderEntries();
};

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

function deleteEntry(event){
    
    const rowId = event.target.getAttribute('data-rowid');
    console.log('Deleting entry with ID:', rowId);

    fetch(`${URL}/entries/${rowId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id: rowId })
    }).then((response) => {
        if (response.ok) {
            console.log('Entry deleted successfully');
            renderEntries(); // Refresh the table after successful deletion
        } else {
            console.error('Failed to delete entry');
        }
    }).catch((error) => {
        console.error('Error:', error);
    });
    
}

const renderEntries = () => {
    const display = document.querySelector('#entryDisplay');
    display.innerHTML = '';

    entries.forEach((entry) => {
        const row = document.createElement('tr');
        var btn = document.createElement('button');
        btn.textContent = "Submit";
        btn.setAttribute('data-rowid', entry.id);
        btn.addEventListener("click", deleteEntry);
        row.appendChild(createCell(entry.id));
        row.appendChild(createCell(new Date(entry.checkIn).toLocaleString()));
        row.appendChild(createCell(new Date(entry.checkOut).toLocaleString()));
        row.appendChild(createCell(entry.category));
        row.appendChild(btn);
        display.appendChild(row);
    });
};

document.addEventListener('DOMContentLoaded', function(){
    const createEntryForm = document.querySelector('#createEntryForm');
    createEntryForm.addEventListener('submit', createEntry);
    indexEntries();
});

/*document.addEventListener('DOMContentLoaded', function() {
    const deleteEntryForm = document.querySelector('#deleteEntryForm');
    deleteEntryForm.addEventListener('submit', deleteEntry);
    indexEntries();
})*/