var vm = new Vue({
    el: '#app',
    data: {
        loginUser: "admin",
        loadTaskSpin: false,
        prefix: "/api/v1/database",
        modalShow: false,
        modalLoading: false,
        submitSpin: false,
        curUserInfo: {
        },
        databaseQuery: {

        },
        databaseInfo: {

        },
        pageInfo: {
            offset: 0,
            current: 0,
            pageSize: 10,
            total: 0
        },
        databaseTypes: [{
            value: 'MySQL',
            label: 'MySQL'
        }, {
            value: 'PostgreSQL',
            label: 'PostgreSQL'
        }, {
            value: 'Sybase',
            label: 'Sybase'
        }, {
            value: 'Oracle',
            label: 'Oracle'
        }, {
            value: 'DaMeng',
            label: '达梦数据库'
        }, {
            value: 'SQLServer',
            label: 'SQL Server'
        }, {
            value: 'KingBase',
            label: '人大金仓'
        }],
        databaseColumns: [
            {
                title: '连接名',
                key: 'name'
            },
            {
                title: '数据库类型',
                key: 'databaseType'
            },
            {
                title: 'IP地址',
                key: 'databaseHost'
            },
            {
                title: '端口',
                key: 'databasePort'
            },
            {
                title: '数据库用户',
                key: 'databaseUser'
            },
            {
                title: '连接的数据库',
                key: 'databaseName'
            },
            {
                title: '最后更新时间',
                key: 'updateTime'
            },
            {
                title: '操作',
                slot: 'action',
                width: 150,
                align: 'center'
            }
         ],
        databaseList: [],
        rules: {
            name: [{
                required: true,
                message: '请填写数据库连接名称',
                trigger: 'blur',
                min: 1,
                max: 300
            }],
            databaseType: [{
                required: true,
                message: '请选择数据库类型',
                trigger: 'blur'
            }],
            databaseHost: [{
                message: '请填写数据库 IP 地址',
                required: true,
                trigger: 'blur',
                max: 300,
                min: 9
            }],
            databasePort: [{
                required: true,
                message: '请填写数据库端口号',
                trigger: 'blur',
                max: 5,
                min: 2
            }],
            databaseUser: [{
                message: '请填写数据库登录用户名',
                trigger: 'blur',
                required: true,
                max: 300,
                min: 1
            }],
            databasePassword: [{
                message: '请填写数据库登录密码',
                trigger: 'blur',
                required: true,
                max: 300,
                min: 1
            }],
            databaseName: [{
                message: '请填写连接的数据库名',
                trigger: 'blur',
                required: true,
                max: 300,
                min: 1
            }]
        }
    },

    methods: {
        init: function() {
            this.loadUserInfo();
            this.loadData();
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
        loadData: function() {
            var _this = this;
            if (this.pageInfo.offset === undefined) {
                this.pageInfo.offset = 0;
            }

            var url = _this.prefix + '?offset=' + this.pageInfo.offset + '&limit=' + this.pageInfo.pageSize;
            for (var key in _this.databaseQuery) {
                var value = _this.databaseQuery[key];
                if (value) {
                    url += '&' + key + '=' + value;
                }
            }

            // _this.openSpin("数据加载中...");
            _this.loadTaskSpin = true;
            axios.get(url).then(function (response) {
                _this.databaseList = response.data.data;
                _this.pageInfo.offset = response.data.pageInfo.offset;
                _this.pageInfo.total = response.data.pageInfo.rowCount;
                _this.loadTaskSpin = false;
            }).catch(function () {
                _this.loadTaskSpin = false;
                _this.$Message.error({content: "加载列表失败！", duration: 2});
            });
        },
        changeOffset: function(current) {
          this.pageInfo.current = current;
          this.pageInfo.offset = this.pageInfo.pageSize * (current - 1);
          this.loadData();
        },
        /**
         * 重置表单.
         */
        resetForm: function () {
            var _this =this;
            for (var key in _this.databaseQuery) {
                _this.databaseQuery[key] = null;
            }
            _this.loadData();
        },

        /**
         * 打开编辑/新增模态框.
         */
        openEditModal: function(obj) {
            var _this = this;
            if (obj) {
                _this.databaseInfo = obj;
            } else {
                _this.databaseInfo = {};
            }
            _this.modalShow = true;
        },

        /**
         * 删除数据库连接.
         */
        deleteDatabaseInfo: function(obj) {
            var _this = this;
            this.$Modal.confirm({
                title: '确认删除',
                content: '确认删除 ' + obj.name +"?",
                onOk: function () {
                    axios.delete(_this.prefix + "/" + obj.id).then(function () {
                        _this.$Message.success("删除成功！");
                        _this.loadData();
                    }).catch(reason => {
                        this.$Message.error({content: reason.response.data.message, duration: 3});
                    })
                }
            })
        },
        /**
         * 切换密码可见性.
         */
        switchPwdType: function () {
            var _this = this;
            if (_this.pwdFlag) {
                //如果当前为可见，则隐藏
                _this.$refs.pwd.type = "password";
                _this.$refs.pwdIcon.type = "eye";
                _this.pwdFlag = false;
            } else {
                //如果当前为不可见，则显示
                _this.$refs.pwd.type = "text";
                _this.$refs.pwdIcon.type = "eye-disabled";
                _this.pwdFlag = true;
            }
        },
        save: function(e) {
            var _this = this;
            this.$refs.databaseInfo.validate((valid) => {
                if (valid) {
                    _this.modalLoading = true;
                    if (this.databaseInfo.id) {
                        axios.put(_this.prefix, _this.databaseInfo).then(function () {
                            _this.$Message.success('更新数据库连接成功');
                            _this.modalShow = false;
                            _this.modalLoading = false;
                        }).catch(function (error) {
                            _this.$Message.error({content: error.response.data.message, duration: 5});
                            _this.modalLoading = false;
                        });
                    } else {
                        axios.post(_this.prefix, _this.databaseInfo).then(function () {
                            _this.$Message.success('添加数据库连接成功');
                            _this.modalShow = false;
                            _this.modalLoading = false;
                            _this.loadData();
                        }).catch(function (error) {
                            _this.$Message.error({content: error.response.data.message, duration: 5});
                            _this.modalLoading = false;
                        });
                    }
                } else {
                    this.$Message.error('校验失败');
                }
            })
        },
        cancel: function () {
            this.modalShow = false;
        }

    },

    created: function() {
        this.init();
    }
});