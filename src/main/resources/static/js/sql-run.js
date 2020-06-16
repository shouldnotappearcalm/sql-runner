var vm = new Vue({
    el: '#app',
    data: {
        loginUser: "admin",
        loadTaskSpin: false,
        prefix: "/api/v1/database",
        curUserInfo: {
        },
        sqlInfoDto: {
            databaseInfoDto:{

            },
            sql: ""
        },
        databaseId: "",
        databaseList: [],
        execResult: {
            row: "",
            dataArray: []
        },
        execResultJsonStr: ""
    },

    methods: {
        init: function() {
            this.loadUserInfo();
            this.loadDatabaseList();
        },

        loadUserInfo: function() {
            axios.get("/api/v1/user/info").then(response => {
                this.curUserInfo = response.data;
                this.loginUser = this.curUserInfo.username;
            }).catch(reason => {
                this.$Message.error({content: reason.response.data.message, duration: 3});
            })
        },
        // 加载列表数据
        loadDatabaseList: function() {
            axios.get(this.prefix + "/all").then(response => {
                this.databaseList = response.data;
            }).catch(reason => {
                this.$Message.error({content: reason.response.data.message, duration: 3});
            })
        },
        // 更换数据库的时候
        databaseChange: function(value) {
            let database = this.databaseList.filter(database => database.id == value);
            this.sqlInfoDto.databaseInfoDto = database[0];
        },

        /**
         * 条件查询.
         */
        execSql: function () {
            var _this = this;
            //校验
            this.$refs["databaseForm"].validate((valid) => {
                if (valid) {
                    this.$Spin.show();
                    axios.post("/api/v1/sql/actions/exec", _this.sqlInfoDto).then(function(response) {
                        _this.execResult = JSON.parse(JSON.stringify(response.data));
                        _this.execResultJsonStr = JSON.stringify(_this.execResult);
                        _this.$Spin.hide();
                    }).catch(function (error) {
                        _this.$Spin.hide();
                        _this.$Message.error({content: error.response.data.message, duration: 5});
                    });
                } else {
                    this.$Message.error('校验失败');
                }
            })
            this.$Spin.show();
            axios.post("/api/v1/sql/actions/exec", _this.sqlInfoDto).then(function(response) {
                _this.execResult = JSON.parse(JSON.stringify(response.data));
                _this.execResultJsonStr = JSON.stringify(_this.execResult);
                _this.$Spin.hide();
            }).catch(function (error) {
                _this.$Spin.hide();
                _this.$Message.error({content: error.response.data.message, duration: 5});
            });
        },

    },

    created: function() {
        this.init();
    }
});