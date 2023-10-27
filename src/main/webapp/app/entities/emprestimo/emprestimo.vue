<template>
  <div>
    <h2 id="page-heading" data-cy="EmprestimoHeading">
      <span v-text="$t('jhipsterApp.emprestimo.home.title')" id="emprestimo-heading">Emprestimos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterApp.emprestimo.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EmprestimoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-emprestimo"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterApp.emprestimo.home.createLabel')"> Create a new Emprestimo </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && emprestimos && emprestimos.length === 0">
      <span v-text="$t('jhipsterApp.emprestimo.home.notFound')">No emprestimos found</span>
    </div>
    <div class="table-responsive" v-if="emprestimos && emprestimos.length > 0">
      <table class="table table-striped" aria-describedby="emprestimos">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dataEmprestimo')">
              <span v-text="$t('jhipsterApp.emprestimo.dataEmprestimo')">Data Emprestimo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dataEmprestimo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estudante.id')">
              <span v-text="$t('jhipsterApp.emprestimo.estudante')">Estudante</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estudante.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('livro.id')">
              <span v-text="$t('jhipsterApp.emprestimo.livro')">Livro</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'livro.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="emprestimo in emprestimos" :key="emprestimo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EmprestimoView', params: { emprestimoId: emprestimo.id } }">{{ emprestimo.id }}</router-link>
            </td>
            <td>{{ emprestimo.dataEmprestimo }}</td>
            <td>
              <div v-if="emprestimo.estudante">
                <router-link :to="{ name: 'EstudanteView', params: { estudanteId: emprestimo.estudante.id } }">{{
                  emprestimo.estudante.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="emprestimo.livro">
                <router-link :to="{ name: 'LivroView', params: { livroId: emprestimo.livro.id } }">{{ emprestimo.livro.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EmprestimoView', params: { emprestimoId: emprestimo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EmprestimoEdit', params: { emprestimoId: emprestimo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(emprestimo)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="jhipsterApp.emprestimo.delete.question" data-cy="emprestimoDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-emprestimo-heading" v-text="$t('jhipsterApp.emprestimo.delete.question', { id: removeId })">
          Are you sure you want to delete this Emprestimo?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-emprestimo"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEmprestimo()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="emprestimos && emprestimos.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./emprestimo.component.ts"></script>
