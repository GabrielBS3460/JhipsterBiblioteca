import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILivro } from '@/shared/model/livro.model';
import LivroService from './livro.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class LivroDetails extends Vue {
  @Inject('livroService') private livroService: () => LivroService;
  @Inject('alertService') private alertService: () => AlertService;

  public livro: ILivro = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.livroId) {
        vm.retrieveLivro(to.params.livroId);
      }
    });
  }

  public retrieveLivro(livroId) {
    this.livroService()
      .find(livroId)
      .then(res => {
        this.livro = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
