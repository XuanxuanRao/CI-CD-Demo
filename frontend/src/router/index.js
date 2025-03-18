import { createRouter, createWebHistory } from 'vue-router'
import { getUserInfo } from '@/api/user'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      component: () => import('@/views/login/loginPage.vue'),
      meta:{requiresParallax:true}
    },
    {
      path: '/',
      component: () => import('@/views/layout/layoutPage.vue'),
      meta:{requiresParallax:false,requiresGradient:true},
      // redirect: '/questionnaire/createQuestionnaire',
      redirect: '/login',
      children: [

        { path: '/questionnaire/questionnaireSquare', component: () => import('@/views/questionnaire/squarePage.vue')},
        
      ]
    }
  ]
})

export default router
