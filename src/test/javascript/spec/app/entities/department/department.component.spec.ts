/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DepartmentComponent from '@/entities/department/department.vue';
import DepartmentClass from '@/entities/department/department.component';
import DepartmentService from '@/entities/department/department.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Department Management Component', () => {
    let wrapper: Wrapper<DepartmentClass>;
    let comp: DepartmentClass;
    let departmentServiceStub: SinonStubbedInstance<DepartmentService>;

    beforeEach(() => {
      departmentServiceStub = sinon.createStubInstance<DepartmentService>(DepartmentService);
      departmentServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DepartmentClass>(DepartmentComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          departmentService: () => departmentServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      departmentServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDepartments();
      await comp.$nextTick();

      // THEN
      expect(departmentServiceStub.retrieve.called).toBeTruthy();
      expect(comp.departments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      departmentServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDepartment();
      await comp.$nextTick();

      // THEN
      expect(departmentServiceStub.delete.called).toBeTruthy();
      expect(departmentServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
