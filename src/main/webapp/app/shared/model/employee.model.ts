import { IJob } from 'app/shared/model/job.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IJobHistory } from 'app/shared/model/job-history.model';

export interface IEmployee {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  hireDate?: string | null;
  salary?: number | null;
  commissionPct?: number | null;
  longF?: number | null;
  intF?: number | null;
  boolF?: boolean | null;
  bigF?: number | null;
  durF?: string | null;
  localDF?: string | null;
  zoneDF?: string | null;
  uuF?: string | null;
  pictContentType?: string | null;
  pict?: string | null;
  comments?: string | null;
  cvContentType?: string | null;
  cv?: string | null;
  jobs?: IJob[] | null;
  manager?: IEmployee | null;
  department?: IDepartment | null;
  jobHistory?: IJobHistory | null;
}

export const defaultValue: Readonly<IEmployee> = {
  boolF: false,
};
