/**
 * Created by sunxyz on 2017/3/17.
 */
//首先使用一个闭包,防止污染全局对象
(function () {
    var api_path = config.api.log;
    Vue.filter('date-formatter', function (value) {
        return moment(value).format('YYYY.MM.DD-HH:mm');
    })
    Vue.filter('url-formatter', function (value) {
        if(value==null){
            return "";
        }
        var url = decodeURIComponent(value);
        return url;//url.replace(/=/g,":").replace(/&/g,",");
    })
    var startDate = moment().subtract(29, 'days').format('YYYY/MM/DD');
    var endDate = moment().format('YYYY/MM/DD');
    var app = new Vue({
        el: "#app",
        data: {
            results: [],
            //查询参数
            name: "",
            startDate: startDate,
            endDate: endDate,
            requestMethod:"GET",

            dataShow: startDate + "-" + endDate,
            selectObj: undefined,
            pageTotal: 0,
            pageCurrent: 1,
            pageSize: 15,
        },
        computed: {
            indexs: function () {
                var left = 1;
                var right = this.pageTotal;
                var ar = [];
                if (this.pageTotal >= 11) {
                    if (this.pageCurrent > 5 && this.pageCurrent < this.pageTotal - 4) {
                        left = this.pageCurrent - 5;
                        right = this.pageCurrent + 4;
                    } else {
                        if (this.pageCurrent <= 5) {
                            left = 1;
                            right = 10;
                        } else {
                            right = this.pageTotal;
                            left = this.pageTotal - 9;
                        }
                    }
                }
                while (left <= right) {
                    ar.push(left);
                    left++;
                }
                return ar;
            },
            showLast: function () {
                if (this.pageCurrent == this.pageTotal || this.pageTotal == 0) {
                    return false;
                }
                return true;
            },
            showFirst: function () {
                if (this.pageCurrent == 1) {
                    return false;
                }
                return true;
            }
        },
        watch: {},
        created: function () {
            //加载数据
            this.loadData();
        },
        methods: {
            pageIndexClick: function (index) {
                if (index != this.pageCurrent) {
                    this.pageCurrent = index;
                }
                this.loadData();
            },
            search: function () {
                this.pageCurrent = 1;
                this.loadData();
            },
            loadData: function () {
                var _this = this;
                var param = {
                    page: _this.pageCurrent - 1,
                    size: _this.pageSize,
                    name: _this.name,
                    requestMethod: _this.requestMethod,
                    startDate: _this.startDate+"/00:00:00",
                    endDate: _this.endDate+"/24:00:00",
                };

                listData(param, function (data) {
                    var ds = data.content;
                    for (var i in ds) {
                        var d = ds[i];
                        d.select = false;
                    }
                    _this.results = ds;
                    _this.pageTotal = data.totalPages;
                });
            },
            select: function (obj) {//添加鼠标选中效果
                obj.select = obj.select ? false : true;
                if (obj.select) {
                    $("tbody tr").removeClass("select");
                    var f = function () {
                        $("#" + obj.id).addClass("select")
                    }
                    setTimeout(f, 100);
                }
                this.selectObj = obj;
            },

            deleteObj: function () {
                deleteObj(this.selectObj)
            },
            load: function () {
                this.loadData();
            }
        }

    });

    //Date picker
    $('.date').daterangepicker({
            locale: {
                applyLabel: '确定',
                cancelLabel: '取消',
                customRangeLabel: '自定义查询'
            },
            ranges: {
                '今天': [moment(), moment()],
                '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                '最近七天': [moment().subtract(6, 'days'), moment()],
                '最近30天': [moment().subtract(29, 'days'), moment()],
                '这个月': [moment().startOf('month'), moment().endOf('month')],
                '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            startDate: moment().subtract(29, 'days'), endDate: moment()
        },
        function (start, end) {
            app.startDate = start.format('YYYY/MM/DD');
            app.endDate = end.format('YYYY/MM/DD');
            app.dataShow = app.startDate + "-" + app.endDate;
        });

    function listData(param, cb) {
        $.get(api_path, param, cb);
    }

    function deleteObj(obj) {
        if (!obj) {
            layer.msg('请选中要删除的列');
            return;
        }
        layer.confirm('您确认删除？', {
            icon: 0,
            btn: ['确定', '取消'] //按钮
        }, function () {
            var id = obj.id;
            $.post(api_path + id, {_method: "DELETE"}, function (data) {
                layer.msg(data.msg, {icon: data.status == "SUCCESS" ? 1 : 2});
                app.load();
            }).error(function () {
                layer.msg("删除失败，不建议删除", {icon: 2});
            })
        }, function () {
            layer.msg('取消成功', {icon: 1});
        });
    }

})();