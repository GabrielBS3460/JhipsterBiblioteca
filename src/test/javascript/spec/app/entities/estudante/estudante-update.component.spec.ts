/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EstudanteUpdateComponent from '@/entities/estudante/estudante-update.vue';
import EstudanteClass from '@/entities/estudante/estudante-update.component';
import EstudanteService from '@/entities/estudante/estudante.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Estudante Management Update Component', () => {
    let wrapper: Wrapper<EstudanteClass>;
    let comp: EstudanteClass;
    let estudanteServiceStub: SinonStubbedInstance<EstudanteService>;

    beforeEach(() => {
      estudanteServiceStub = sinon.createStubInstance<EstudanteService>(EstudanteService);

      wrapper = shallowMount<EstudanteClass>(EstudanteUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          estudanteService: () => estudanteServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.estudante = entity;
        estudanteServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(estudanteServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.estudante = entity;
        estudanteServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(estudanteServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEstudante = { id: 123 };
        estudanteServiceStub.find.resolves(foundEstudante);
        estudanteServiceStub.retrieve.resolves([foundEstudante]);

        // WHEN
        comp.beforeRouteEnter({ params: { estudanteId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.estudante).toBe(foundEstudante);
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
