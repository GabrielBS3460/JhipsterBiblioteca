<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="emprestimo">
        <h2 class="jh-entity-heading" data-cy="emprestimoDetailsHeading">
          <span v-text="$t('jhipsterApp.emprestimo.detail.title')">Emprestimo</span> {{ emprestimo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterApp.emprestimo.dataEmprestimo')">Data Emprestimo</span>
          </dt>
          <dd>
            <span>{{ emprestimo.dataEmprestimo }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterApp.emprestimo.estudante')">Estudante</span>
          </dt>
          <dd>
            <div v-if="emprestimo.estudante">
              <router-link :to="{ name: 'EstudanteView', params: { estudanteId: emprestimo.estudante.id } }">{{
                emprestimo.estudante.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterApp.emprestimo.livro')">Livro</span>
          </dt>
          <dd>
            <div v-if="emprestimo.livro">
              <router-link :to="{ name: 'LivroView', params: { livroId: emprestimo.livro.id } }">{{ emprestimo.livro.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="emprestimo.id"
          :to="{ name: 'EmprestimoEdit', params: { emprestimoId: emprestimo.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./emprestimo-details.component.ts"></script>
