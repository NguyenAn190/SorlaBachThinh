var charts;
$(function () {
    var profitDataElement = document.getElementById("profitData");
    var profitData = [140000000, 140000000, 112000000, 134000000, 147000000, 148000000, 133000000, 122000000, 140000000, 145000000, 130000000, 130000000];
    if (profitDataElement !== null) {
        profitData = JSON.parse(profitDataElement.getAttribute("data-profit"));
    }

    // Profit
    var chart = {
        series: [{
            name: "Lợi nhuận tháng này", data: Object.values(profitData)
        }],

        chart: {
            type: "bar",
            height: 345,
            offsetX: -15,
            toolbar: {show: true},
            foreColor: "#adb0bb",
            fontFamily: 'inherit',
            sparkline: {enabled: false},
        },

        colors: ["#5D87FF", "#49BEFF"],

        plotOptions: {
            bar: {
                horizontal: false,
                columnWidth: "35%",
                borderRadius: [6],
                borderRadiusApplication: 'end',
                borderRadiusWhenStacked: 'all'
            },
        }, markers: {size: 0},

        dataLabels: {
            enabled: false,
        },

        legend: {
            show: false,
        },

        grid: {
            borderColor: "rgba(0,0,0,0.1)", strokeDashArray: 3, xaxis: {
                lines: {
                    show: false,
                },
            },
        },

        xaxis: {
            type: "category", categories: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"], labels: {
                style: {cssClass: "grey--text lighten-2--text fill-color"},
            },
        },

        yaxis: {
            show: true,
            min: 0,
            max: 1000000000,
            tickAmount: 5,
            labels: {
                style: {
                    cssClass: 'grey--text lighten-2--text fill-color',
                },
                formatter: function (value) {
                    return formatCurrency(value);
                },
            },
        },

        tooltip: {theme: "light"},

        responsive: [{
            breakpoint: 600, options: {
                plotOptions: {
                    bar: {
                        borderRadius: 3,
                    }
                },
            }
        }]
    };
    charts = new ApexCharts(document.querySelector("#chart"), chart);
    charts.render();


    // Breakup
    var breakup = {
        color: "#adb5bd", series: [38, 40, 25], labels: ["2022", "2021", "2020"], chart: {
            width: 180, type: "donut", fontFamily: "Plus Jakarta Sans', sans-serif", foreColor: "#adb0bb",
        }, plotOptions: {
            pie: {
                startAngle: 0, endAngle: 360, donut: {
                    size: '75%',
                },
            },
        }, stroke: {
            show: false,
        },

        dataLabels: {
            enabled: false,
        },

        legend: {
            show: false,
        }, colors: ["#5D87FF", "#ecf2ff", "#F9F9FD"],

        responsive: [{
            breakpoint: 991, options: {
                chart: {
                    width: 150,
                },
            },
        },], tooltip: {
            theme: "dark", fillSeriesColor: false,
        },
    };
    var chart = new ApexCharts(document.querySelector("#breakup"), breakup);
    chart.render();


    // Earning
    var earning = {
        chart: {
            id: "sparkline3", type: "area", height: 60, sparkline: {
                enabled: true,
            }, group: "sparklines", fontFamily: "Plus Jakarta Sans', sans-serif", foreColor: "#adb0bb",
        }, series: [{
            name: "Earnings", color: "#49BEFF", data: [25, 66, 20, 40, 12, 58, 20],
        },], stroke: {
            curve: "smooth", width: 2,
        }, fill: {
            colors: ["#f3feff"], type: "solid", opacity: 0.05,
        },

        markers: {
            size: 0,
        }, tooltip: {
            theme: "dark", fixed: {
                enabled: true, position: "right",
            }, x: {
                show: false,
            },
        },
    };
    new ApexCharts(document.querySelector("#earning"), earning).render();
})

function formatCurrency(value) {
    var formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
        minimumFractionDigits: 0,
    });
    return formatter.format(value);
}
