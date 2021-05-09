import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRegion } from '@/shared/model/region.model';
import RegionService from './region.service';

@Component
export default class RegionDetails extends Vue {
  @Inject('regionService') private regionService: () => RegionService;
  public region: IRegion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regionId) {
        vm.retrieveRegion(to.params.regionId);
      }
    });
  }

  public retrieveRegion(regionId) {
    this.regionService()
      .find(regionId)
      .then(res => {
        this.region = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
