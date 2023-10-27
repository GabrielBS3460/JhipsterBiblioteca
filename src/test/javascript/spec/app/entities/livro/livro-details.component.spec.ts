/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LivroDetailComponent from '@/entities/livro/livro-details.vue';
import LivroClass from '@/entities/livro/livro-details.component';
import LivroService from '@/entities/livro/livro.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Livro Management Detail Component', () => {
    let wrapper: Wrapper<LivroClass>;
    let comp: LivroClass;
    let livroServiceStub: SinonStubbedInstance<LivroService>;

    beforeEach(() => {
      livroServiceStub = sinon.createStubInstance<LivroService>(LivroService);

      wrapper = shallowMount<LivroClass>(LivroDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { livroService: () => livroServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLivro = { id: 123 };
        livroServiceStub.find.resolves(foundLivro);

        // WHEN
        comp.retrieveLivro(123);
        await comp.$nextTick();

        // THEN
        expect(comp.livro).toBe(foundLivro);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLivro = { id: 123 };
        livroServiceStub.find.resolves(foundLivro);

        // WHEN
        comp.beforeRouteEnter({ params: { livroId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.livro).toBe(foundLivro);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
