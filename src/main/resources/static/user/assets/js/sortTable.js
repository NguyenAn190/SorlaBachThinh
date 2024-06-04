$(document).ready(function () {
    $('#sortHistoryOrder').DataTable({
        "order": [],
        "paging": false,
        "ordering": true,
        "info": true,
        language: {
            url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/vi.json',
        },
    })
});
