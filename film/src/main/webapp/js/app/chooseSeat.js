var app = new Vue({
    el: '#app',
    data: {
        movieInfo: {},
        selectedSeats: [],
        unAvailableSeats: [],
        chooseSeatInfo: []
    },
    watch: {
        unAvailableSeats: function (newValue, oldValue) {

            $.each(newValue, function (index, value) {
                $("#" + value).removeClass("available").addClass("unavailable");//改成已售出
            })
        }
    },
    methods: {
        formatSeat: function (seat) {
            var strs = seat.split('_');
            return strs[0] + '排' + strs[1] + '座';
        },
        choose: function (event) {
            console.log(event);
            var target = event.currentTarget;
            console.log(target);
            console.log($(target).attr("i" +
                "d"));
            var id = $(target).attr("id");

            if (this.unAvailableSeats.includes(id)) {
                return;
            }

            if (!this.selectedSeats.includes(id)) {
                $(target).removeClass("available").addClass("selected");//改成选中
                this.selectedSeats.push(id);
            } else {
                $(target).removeClass("selected").addClass("available");
                this.selectedSeats.splice(this.selectedSeats.indexOf(id), 1);//改成可选
            }
        },
        insertOrder: function () {
            var showTimeId = UrlParam.param("showTimeId");
            $.ajax({
                type: 'post', //默认get
                url: "/api/order/insertOrder.do",
                data: {
                    showTimeId: showTimeId,
                    movieId: 59,
                    selectedSeats: app.selectedSeats
                },
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        $("#app").html(data.data);
                    } else {
                        alert(data.errorMsg);
                        if (data.errorCode === 111) {
                            window.location.href = "/login.html";
                        }
                        else {
                            app.listSaledSeats();
                        }
                    }
                }
            });

        },
        getMovieInfo: function () {
            var showTimeId = UrlParam.param("showTimeId");
            $.ajax({
                type: 'post', //默认get
                url: "/api/getMovieInfoByShowTimeId.do",
                data: {
                    showTimeId: showTimeId,
                },
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        app.movieInfo = data.data;
                    } else {
                        alert(data.errorMsg);
                    }
                    app.listSaledSeats();
                }
            });

        },
        listSaledSeats: function () {
            var showTimeId = UrlParam.param("showTimeId");
            $.ajax({
                type: 'post', //默认get
                url: "/api/order/listSaledSeats.do",
                data: {
                    showTimeId: showTimeId
                },
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        app.unAvailableSeats = data.data;
                    } else {
                        alert(data.errorMsg)
                    }
                }
            });
        }
    },
    mounted: function () {
        this.getMovieInfo();
        // this.listSaledSeats();
    }
});
