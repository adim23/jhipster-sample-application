import { ICountry } from 'app/shared/model/country.model';
import { IDepartment } from 'app/shared/model/department.model';

export interface ILocation {
  id?: number;
  streetAddress?: string | null;
  postalCode?: string | null;
  city?: string | null;
  stateProvince?: string | null;
  country?: ICountry | null;
  departments?: IDepartment[] | null;
}

export const defaultValue: Readonly<ILocation> = {};
