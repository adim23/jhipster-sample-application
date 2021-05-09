import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILocation } from '@/shared/model/location.model';
import LocationService from './location.service';

@Component
export default class LocationDetails extends Vue {
  @Inject('locationService') private locationService: () => LocationService;
  public location: ILocation = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.locationId) {
        vm.retrieveLocation(to.params.locationId);
      }
    });
  }

  public retrieveLocation(locationId) {
    this.locationService()
      .find(locationId)
      .then(res => {
        this.location = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
