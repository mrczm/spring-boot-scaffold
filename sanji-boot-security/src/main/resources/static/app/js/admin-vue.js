// header
Vue.component('admin-header', {
    template: '#admin-header',
    props: {
        title: [String],
        system_list: [Array],
        user_menus: [Array]
    },
    data: function () {
        return {
            search_text: ''
        }
    },
    methods: {
        //将事件提交给父组件
        sys_switch: function (sys) {
            this.title = sys.title;
            this.$emit('click_sys_switch', sys)
        },
        search: function () {
            this.$emit('click_search', this.search_text)
        }
    }
})
//main
Vue.component('admin-main', {
    template: '#admin-main',
    props: {
        user: [Object],
        user_menus: [Array],
        menus: [Array],
    },
    methods: {
        //将事件提交给父组件
        open_tab: function (title, url) {
            this.$emit('click_open_tab', title, url);
        }
    }
})

//sidebar
Vue.component('admin-sidebar', {
    template: '#admin-sidebar',
    props: {
        user: [Object],
        user_menus: [Array],
        menus: [Array]
    },
    methods: {
        //将事件提交给父组件
        open_tab: function (menu) {
            this.$emit('open_tab', menu.title, menu.url);
        }
    }
})

var data = {
    server_name: 'bloom-upms-server',
    //header
    system_title: '权限管理系统',
    system_list: [
        {id: '1', name: 'bloom-upms-server', title: '权限管理系统', icon: 'zmdi-shield-security'},
        {id: '2', name: 'bloom-cms-admin', title: '内容管理系统', icon: 'zmdi-wikipedia'},
        {id: '3', name: 'bloom-pay-admin', title: '支付管理系统', icon: 'zmdi-paypal-alt'},
        {id: '4', name: 'bloom-ucenter-home', title: '用户管理系统', icon: 'zmdi-account'},
        {id: '5', name: 'bloom-oss-web', title: '存储管理系统', icon: 'zmdi-cloud'},
    ],
    //main
    user_menus: [
        {title: '个人资料', icon: 'zmdi-account', url: '', isOpenTab: true},
        {title: '隐私管理', icon: 'zmdi-face', url: '', isOpenTab: true},
        {title: '退出登录', icon: 'zmdi-run', url: 'logout', isOpenTab: false}
    ],
    user: {
        avater: 'app/img/avatar.jpg',
        name: 'bloom'
    },
    menus: [
        {title: '首页', icon: 'zmdi-home', url: 'home', isOpenTab: true},
        {
            title: '系统组织管理', icon: 'zmdi-accounts-list', childer: [
            {title: '系统管理', icon: 'zmdi-account', url: 'crud.html', isOpenTab: true}
        ]
        },
        {
            title: ' 角色用户管理', icon: 'zmdi-accounts', childer: [
            {title: '用户管理', icon: '', url: 'page/sys/user-crud.html', isOpenTab: true}
        ]
        },
        {
            title: '其他数据管理', icon: 'zmdi-more', childer: [
            {title: '百度', icon: 'zmdi-lock-outline', url: 'https://www.baidu.com/', isOpenTab: true},
        ]
        }
    ]
}

var vue = new Vue({
    el: '#app',
    data: data,
    created: function () {
        this.server_name = $.cookie('bloom-skin-name') || this.system_list[0].name
        this.system_title = $.cookie('bloom-system-title') || this.system_list[0].title
    },
    methods: {
        sys_switch: function (sys) {// 切换系统
            $.cookie('bloom-skin-name', sys.name)
            $.cookie('bloom-system-title', sys.title);
            this.server_name = sys.name;
        },
        search: function (q) {
            console.log(q);
        },
        open_tab: function (title, url) {
            console.log(title, url)
            Tab.addTab(title, url)
        }
    }
})