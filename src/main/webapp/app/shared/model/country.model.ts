import { IRegion } from 'app/shared/model/region.model';
import { ILocation } from 'app/shared/model/location.model';

export interface ICountry {
  id?: number;
  countryName?: string | null;
  region?: IRegion | null;
  location?: ILocation | null;
}

export const defaultValue: Readonly<ICountry> = {};
