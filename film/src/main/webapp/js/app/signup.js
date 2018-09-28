    var app = new Vue({
    el: '#app',
    data: {
        movie: {},
        selectedSeats: [],//已选择
        unavailableSeats: []//已售出
    },
    methods: {
        sendCode:function(){

            var loginName = $("#loginName").val();
            if(!loginName){
                alert("请输入邮箱");
                return;
            }

            $("#btn_sendcode").attr('disabled','disabled').html('发送中');

            $.ajax({
                type: 'post', //默认get
                url: "/api/user/sendCode.do",
                data: {email: loginName},
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                       alert("发送成功");
                        app.codeCount(10);
                    } else {
                        alert(data.errorMsg)
                    }

                }
            });
        }, signup:function(){


            $.ajax({
                type: 'post', //默认get
                url: "/api/user/signup.do",
                data: $("#form1").serialize(),
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        alert("注册成功");
                    } else {
                        alert(data.errorMsg)
                    }

                }
            });
        },
        codeCount:function (c) {
            if (c === 0) {
                $("#btn_sendcode").removeAttr('disabled').html('发送验证码');
                return;
            }
            $("#btn_sendcode").html(c--);
            setTimeout(function () {
                app.codeCount(c);
            },1000)

        }
    }
});