import { createApp } from 'vue'
import App from './App.vue'
import {router} from './router'
import Vue3Toastify from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

import 'bootstrap/dist/css/bootstrap.css'
import bootstrap from 'bootstrap/dist/js/bootstrap.bundle.js'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'


const vuetify = createVuetify({
    components,
    directives,
})


createApp(App).use(router).use(bootstrap).use(vuetify).use(Vue3Toastify, {
    autoClose: 3000, }).mount('#app')

