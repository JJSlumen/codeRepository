var app = new Vue({
    el: '#app',
    data: {

    },
    methods: {
        sendCode:function(){
            var email = $("#email").val();
            if (!email) {
                alert("请输入邮箱");
                return;
            }
            $("#btn_sendcode").attr('disabled', 'disabled').html('发送中');
            $.ajax({
                type: 'post', //默认get
                url: "/user/sendCode",
                data: {email: email},
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        app.codeCont(10);//读秒
                    } else {
                        alert(data.errorMsg)
                    }

                }
            });
        },
        codeCont: function (c) {

            if (c === 0) {
                $("#btn_sendcode").removeAttr('disabled').html('发送验证码');
                return;
            }

            $("#btn_sendcode").html(c--);

            setTimeout(function () {
                app.codeCont(c);
            }, 1000);
        }
    },
    mounted: function () {

    }
});