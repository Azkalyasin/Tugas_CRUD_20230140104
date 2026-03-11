$(document).ready(function() {
    const API_URL = '/ktp';

    // Load Data on Start
    loadKtpData();

    // Form Submit (Add/Update)
    $('#ktp-form').submit(function(e) {
        e.preventDefault();
        
        const id = $('#ktp-id').val();
        const ktpData = {
            nomorKtp: $('#nomorKtp').val(),
            namaLengkap: $('#namaLengkap').val(),
            alamat: $('#alamat').val(),
            tanggalLahir: $('#tanggalLahir').val(),
            jenisKelamin: $('#jenisKelamin').val()
        };

        if (id) {
            updateKtp(id, ktpData);
        } else {
            addKtp(ktpData);
        }
    });

    // Cancel Edit
    $('#cancel-btn').click(function() {
        resetForm();
    });

    // Search functionality
    $('#search-input').on('keyup', function() {
        const value = $(this).val().toLowerCase();
        $("#ktp-list tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    function loadKtpData() {
        $.ajax({
            url: API_URL,
            method: 'GET',
            success: function(response) {
                if (response.success) {
                    displayKtpData(response.data);
                } else {
                    showNotification('Error: ' + response.message, 'error');
                }
            },
            error: function() {
                showNotification('Failed to connect to server', 'error');
            }
        });
    }

    function displayKtpData(data) {
        const list = $('#ktp-list');
        list.empty();
        
        if (data.length === 0) {
            list.append('<tr><td colspan="7" style="text-align:center; padding: 2rem; color: #64748b;">No data available</td></tr>');
            return;
        }

        data.forEach((ktp, index) => {
            list.append(`
                <tr>
                    <td>${index + 1}</td>
                    <td>${ktp.nomorKtp}</td>
                    <td>${ktp.namaLengkap}</td>
                    <td>${ktp.alamat}</td>
                    <td>${ktp.tanggalLahir}</td>
                    <td>${ktp.jenisKelamin}</td>
                    <td>
                        <div class="action-btns">
                            <button class="btn-icon btn-edit" onclick="editKtp(${ktp.id})" title="Edit">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-icon btn-delete" onclick="deleteKtp(${ktp.id})" title="Delete">
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `);
        });
    }

    function addKtp(data) {
        $.ajax({
            url: API_URL,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                if (response.success) {
                    showNotification('Data added successfully!', 'success');
                    resetForm();
                    loadKtpData();
                } else {
                    showNotification(response.message, 'error');
                }
            },
            error: function(xhr) {
                const response = xhr.responseJSON;
                showNotification(response ? response.message : 'Failed to add data', 'error');
            }
        });
    }

    function updateKtp(id, data) {
        $.ajax({
            url: `${API_URL}/${id}`,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                if (response.success) {
                    showNotification('Data updated successfully!', 'success');
                    resetForm();
                    loadKtpData();
                } else {
                    showNotification(response.message, 'error');
                }
            },
            error: function(xhr) {
                const response = xhr.responseJSON;
                showNotification(response ? response.message : 'Failed to update data', 'error');
            }
        });
    }

    window.editKtp = function(id) {
        $.ajax({
            url: `${API_URL}/${id}`,
            method: 'GET',
            success: function(response) {
                if (response.success) {
                    const ktp = response.data;
                    $('#ktp-id').val(ktp.id);
                    $('#nomorKtp').val(ktp.nomorKtp);
                    $('#namaLengkap').val(ktp.namaLengkap);
                    $('#alamat').val(ktp.alamat);
                    $('#tanggalLahir').val(ktp.tanggalLahir);
                    $('#jenisKelamin').val(ktp.jenisKelamin);
                    
                    $('#form-title').text('Edit KTP Data');
                    $('#submit-btn').text('Update Data');
                    $('#cancel-btn').show();
                    
                    window.scrollTo({ top: 0, behavior: 'smooth' });
                }
            }
        });
    };

    window.deleteKtp = function(id) {
        if (confirm('Are you sure you want to delete this data?')) {
            $.ajax({
                url: `${API_URL}/${id}`,
                method: 'DELETE',
                success: function(response) {
                    if (response.success) {
                        showNotification('Data deleted successfully!', 'success');
                        loadKtpData();
                    } else {
                        showNotification(response.message, 'error');
                    }
                },
                error: function() {
                    showNotification('Failed to delete data', 'error');
                }
            });
        }
    };

    function resetForm() {
        $('#ktp-form')[0].reset();
        $('#ktp-id').val('');
        $('#form-title').text('Add New KTP');
        $('#submit-btn').text('Save Data');
        $('#cancel-btn').hide();
    }

    function showNotification(message, type) {
        const container = $('#notification-container');
        const icon = type === 'success' ? 'fa-check-circle' : 'fa-exclamation-triangle';
        const notification = $(`
            <div class="notification ${type}">
                <i class="fas ${icon}"></i>
                <span>${message}</span>
            </div>
        `);
        
        container.append(notification);
        
        setTimeout(() => {
            notification.fadeOut(300, function() {
                $(this).remove();
            });
        }, 3000);
    }
});
