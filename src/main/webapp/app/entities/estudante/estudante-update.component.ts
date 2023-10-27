import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IEstudante, Estudante } from '@/shared/model/estudante.model';
import EstudanteService from './estudante.service';

const validations: any = {
  estudante: {
    primeiroNome: {
      required,
    },
    sobrenome: {
      required,
    },
    email: {
      required,
    },
    idade: {
      required,
      numeric,
    },
  },
};

@Component({
  validations,
})
export default class EstudanteUpdate extends Vue {
  @Inject('estudanteService') private estudanteService: () => EstudanteService;
  @Inject('alertService') private alertService: () => AlertService;

  public estudante: IEstudante = new Estudante();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.estudanteId) {
        vm.retrieveEstudante(to.params.estudanteId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.estudante.id) {
      this.estudanteService()
        .update(this.estudante)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterApp.estudante.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.estudanteService()
        .create(this.estudante)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterApp.estudante.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveEstudante(estudanteId): void {
    this.estudanteService()
      .find(estudanteId)
      .then(res => {
        this.estudante = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
