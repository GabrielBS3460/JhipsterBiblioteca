import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEstudante } from '@/shared/model/estudante.model';
import EstudanteService from './estudante.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EstudanteDetails extends Vue {
  @Inject('estudanteService') private estudanteService: () => EstudanteService;
  @Inject('alertService') private alertService: () => AlertService;

  public estudante: IEstudante = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.estudanteId) {
        vm.retrieveEstudante(to.params.estudanteId);
      }
    });
  }

  public retrieveEstudante(estudanteId) {
    this.estudanteService()
      .find(estudanteId)
      .then(res => {
        this.estudante = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
