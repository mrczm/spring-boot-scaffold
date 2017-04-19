/**
 * Created by sunxyz on 2017/3/17.
 */
(function () {
    var id = 0;
    var path = config.api.menu;
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form()
            , layer = layui.layer

        //自定义验证规则
        form.verify({
            name: function (value) {
                if (value.length < 3) {
                    return '名称至少得3个字符啊';
                }
            }
        });

        //监听提交
        form.on('submit(demo1)', function (data) {
            if (id) {
                edit()
            } else {
                add();
            }
            return false;
        });
        var f = function () {
            var obj = JSON.parse($("#json").val());
            id = obj.id;
//            console.log(obj)
            var app = new Vue({
                el: '#app',
                data: {
                    obj: obj,
                    icon: obj.icon,
                    isChild: obj.isParent
                }

            })
            console.log(obj)
            if(obj.visible){
                $("#visible").attr("checked",true);
                // console.log("------------------")
            }
            form.render();
        }
        setTimeout(f, 200)
    });

    function edit() {
        var url = path + id;
        var method = "PUT";
        save(url, method);
    }

    function add() {
        var url = path;
        var method = "POST";
        save(url, method);
    }

    function save(url, method) {
        var options = {
            url: url,
            method: method,
            success: function (data) {
                layer.msg(data.msg)
            }
        };
        $("#app").ajaxSubmit(options)
        setTimeout(function () {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        }, 3000);
    }
})();