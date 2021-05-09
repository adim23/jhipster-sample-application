/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LocationComponent from '@/entities/location/location.vue';
import LocationClass from '@/entities/location/location.component';
import LocationService from '@/entities/location/location.service';

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
  describe('Location Management Component', () => {
    let wrapper: Wrapper<LocationClass>;
    let comp: LocationClass;
    let locationServiceStub: SinonStubbedInstance<LocationService>;

    beforeEach(() => {
      locationServiceStub = sinon.createStubInstance<LocationService>(LocationService);
      locationServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LocationClass>(LocationComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          locationService: () => locationServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      locationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLocations();
      await comp.$nextTick();

      // THEN
      expect(locationServiceStub.retrieve.called).toBeTruthy();
      expect(comp.locations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      locationServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeLocation();
      await comp.$nextTick();

      // THEN
      expect(locationServiceStub.delete.called).toBeTruthy();
      expect(locationServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
