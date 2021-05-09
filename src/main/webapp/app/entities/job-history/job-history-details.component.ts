import { Component, Vue, Inject } from 'vue-property-decorator';

import { IJobHistory } from '@/shared/model/job-history.model';
import JobHistoryService from './job-history.service';

@Component
export default class JobHistoryDetails extends Vue {
  @Inject('jobHistoryService') private jobHistoryService: () => JobHistoryService;
  public jobHistory: IJobHistory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.jobHistoryId) {
        vm.retrieveJobHistory(to.params.jobHistoryId);
      }
    });
  }

  public retrieveJobHistory(jobHistoryId) {
    this.jobHistoryService()
      .find(jobHistoryId)
      .then(res => {
        this.jobHistory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
