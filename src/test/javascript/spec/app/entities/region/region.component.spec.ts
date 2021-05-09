/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RegionComponent from '@/entities/region/region.vue';
import RegionClass from '@/entities/region/region.component';
import RegionService from '@/entities/region/region.service';

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
  describe('Region Management Component', () => {
    let wrapper: Wrapper<RegionClass>;
    let comp: RegionClass;
    let regionServiceStub: SinonStubbedInstance<RegionService>;

    beforeEach(() => {
      regionServiceStub = sinon.createStubInstance<RegionService>(RegionService);
      regionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RegionClass>(RegionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          regionService: () => regionServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      regionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRegions();
      await comp.$nextTick();

      // THEN
      expect(regionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.regions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      regionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeRegion();
      await comp.$nextTick();

      // THEN
      expect(regionServiceStub.delete.called).toBeTruthy();
      expect(regionServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
