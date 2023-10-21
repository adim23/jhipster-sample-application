import { IEmployee } from 'app/shared/model/employee.model';
import { ILocation } from 'app/shared/model/location.model';
import { IJobHistory } from 'app/shared/model/job-history.model';

export interface IDepartment {
  id?: number;
  departmentName?: string;
  employees?: IEmployee[] | null;
  location?: ILocation | null;
  location?: ILocation | null;
  jobHistory?: IJobHistory | null;
}

export const defaultValue: Readonly<IDepartment> = {};
