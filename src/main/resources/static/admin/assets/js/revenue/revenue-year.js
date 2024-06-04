function updateChart() {
    var selectedYear = $("#yearSelector").val();
    var revenueText = $("#revenue");
    var namDanhThu = $("#nam-doanh-thu");

    var topSellingProducts_Id = $("#topSellingProducts_Id");

    namDanhThu.empty();
    topSellingProducts_Id.empty();

    // Kiểm tra xem biểu đồ đã được khởi tạo chưa
    if (charts) {
        var url = "/admin/doanhthu/load-bieu-do?year=" + selectedYear;

        $.ajax({
            url: url,
            type: "GET",
            success: function (data) {
                var revenue = data.revenue;
                var profitData = data.profitData;
                var topSellingProducts = data.topSellingProducts;
                console.log(topSellingProducts)

                // Cập nhật dữ liệu trong biểu đồ
                charts.updateOptions({
                    series: [{
                        data: Object.values(profitData)
                    }]
                });

                revenueText.text(formatCurrency(revenue));
                namDanhThu.append(`
                    <div class="me-4">
                        <span class="round-8 bg-primary rounded-circle me-2 d-inline-block"></span>
                        <span class="fs-2">${selectedYear}</span>
                    </div>
                    <div>
                        <span class="round-8 bg-light-primary rounded-circle me-2 d-inline-block"></span>
                        <span class="fs-2">${selectedYear}</span>
                    </div>
                `);

                for (const topSellingProduct of topSellingProducts) {
                    topSellingProducts_Id.append(`
                                                <tr>
                                                <td class="border-bottom-0">
                                                    <p class="mb-0 fw-normal">${topSellingProduct[0]}</p>
                                                </td>
                                                <td class="border-bottom-0">
                                                    <span class="fw-normal">${topSellingProduct[1]}</span>
                                                </td>
                                                <td class="border-bottom-0">
                                                    <p class="mb-0 fw-normal">${topSellingProduct[2]}
                                                        Admin</p>
                                                </td>
                                                <td class="border-bottom-0">
                                                    <div class="d-flex align-items-center gap-2">
                                                        <span class="badge bg-primary rounded-3 fw-semibold">${topSellingProduct[3]}</span>
                                                    </div>
                                                </td>
                                                <td class="border-bottom-0">
                                                    <p class="mb-0 fw-normal">
                                                    ${formatCurrency(topSellingProduct[4])}</p>
                                                </td>
                                            </tr>
                        `);
                }
            },
            error: function (error) {
                console.error("Lỗi khi lấy dữ liệu: " + error.responseText);
            }
        });
    } else {
        console.error("Biểu đồ không tồn tại. Hãy đảm bảo rằng bạn đã khởi tạo biểu đồ trước khi cố gắng cập nhật nó.");
    }
}