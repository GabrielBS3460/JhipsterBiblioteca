import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Estudante = () => import('@/entities/estudante/estudante.vue');
// prettier-ignore
const EstudanteUpdate = () => import('@/entities/estudante/estudante-update.vue');
// prettier-ignore
const EstudanteDetails = () => import('@/entities/estudante/estudante-details.vue');
// prettier-ignore
const Livro = () => import('@/entities/livro/livro.vue');
// prettier-ignore
const LivroUpdate = () => import('@/entities/livro/livro-update.vue');
// prettier-ignore
const LivroDetails = () => import('@/entities/livro/livro-details.vue');
// prettier-ignore
const Emprestimo = () => import('@/entities/emprestimo/emprestimo.vue');
// prettier-ignore
const EmprestimoUpdate = () => import('@/entities/emprestimo/emprestimo-update.vue');
// prettier-ignore
const EmprestimoDetails = () => import('@/entities/emprestimo/emprestimo-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'estudante',
      name: 'Estudante',
      component: Estudante,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estudante/new',
      name: 'EstudanteCreate',
      component: EstudanteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estudante/:estudanteId/edit',
      name: 'EstudanteEdit',
      component: EstudanteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estudante/:estudanteId/view',
      name: 'EstudanteView',
      component: EstudanteDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'livro',
      name: 'Livro',
      component: Livro,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'livro/new',
      name: 'LivroCreate',
      component: LivroUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'livro/:livroId/edit',
      name: 'LivroEdit',
      component: LivroUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'livro/:livroId/view',
      name: 'LivroView',
      component: LivroDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emprestimo',
      name: 'Emprestimo',
      component: Emprestimo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emprestimo/new',
      name: 'EmprestimoCreate',
      component: EmprestimoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emprestimo/:emprestimoId/edit',
      name: 'EmprestimoEdit',
      component: EmprestimoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emprestimo/:emprestimoId/view',
      name: 'EmprestimoView',
      component: EmprestimoDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
