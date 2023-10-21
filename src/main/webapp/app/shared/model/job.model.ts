import { ITask } from 'app/shared/model/task.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IJobHistory } from 'app/shared/model/job-history.model';

export interface IJob {
  id?: number;
  jobTitle?: string | null;
  minSalary?: number | null;
  maxSalary?: number | null;
  tasks?: ITask[] | null;
  employee?: IEmployee | null;
  jobHistory?: IJobHistory | null;
}

export const defaultValue: Readonly<IJob> = {};
