import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import EstudanteService from '@/entities/estudante/estudante.service';
import { IEstudante } from '@/shared/model/estudante.model';

import LivroService from '@/entities/livro/livro.service';
import { ILivro } from '@/shared/model/livro.model';

import { IEmprestimo, Emprestimo } from '@/shared/model/emprestimo.model';
import EmprestimoService from './emprestimo.service';

const validations: any = {
  emprestimo: {
    dataEmprestimo: {},
  },
};

@Component({
  validations,
})
export default class EmprestimoUpdate extends Vue {
  @Inject('emprestimoService') private emprestimoService: () => EmprestimoService;
  @Inject('alertService') private alertService: () => AlertService;

  public emprestimo: IEmprestimo = new Emprestimo();

  @Inject('estudanteService') private estudanteService: () => EstudanteService;

  public estudantes: IEstudante[] = [];

  @Inject('livroService') private livroService: () => LivroService;

  public livros: ILivro[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.emprestimoId) {
        vm.retrieveEmprestimo(to.params.emprestimoId);
      }
      vm.initRelationships();
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
    if (this.emprestimo.id) {
      this.emprestimoService()
        .update(this.emprestimo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterApp.emprestimo.updated', { param: param.id });
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
      this.emprestimoService()
        .create(this.emprestimo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterApp.emprestimo.created', { param: param.id });
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

  public retrieveEmprestimo(emprestimoId): void {
    this.emprestimoService()
      .find(emprestimoId)
      .then(res => {
        this.emprestimo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.estudanteService()
      .retrieve()
      .then(res => {
        this.estudantes = res.data;
      });
    this.livroService()
      .retrieve()
      .then(res => {
        this.livros = res.data;
      });
  }
}
