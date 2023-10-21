import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IDepartment } from 'app/shared/model/department.model';
import { getEntities as getDepartments } from 'app/entities/department/department.reducer';
import { IJobHistory } from 'app/shared/model/job-history.model';
import { getEntities as getJobHistories } from 'app/entities/job-history/job-history.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';

export const EmployeeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employees = useAppSelector(state => state.employee.entities);
  const departments = useAppSelector(state => state.department.entities);
  const jobHistories = useAppSelector(state => state.jobHistory.entities);
  const employeeEntity = useAppSelector(state => state.employee.entity);
  const loading = useAppSelector(state => state.employee.loading);
  const updating = useAppSelector(state => state.employee.updating);
  const updateSuccess = useAppSelector(state => state.employee.updateSuccess);

  const handleClose = () => {
    navigate('/employee' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployees({}));
    dispatch(getDepartments({}));
    dispatch(getJobHistories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.hireDate = convertDateTimeToServer(values.hireDate);
    values.zoneDF = convertDateTimeToServer(values.zoneDF);

    const entity = {
      ...employeeEntity,
      ...values,
      manager: employees.find(it => it.id.toString() === values.manager.toString()),
      department: departments.find(it => it.id.toString() === values.department.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          hireDate: displayDefaultDateTime(),
          zoneDF: displayDefaultDateTime(),
        }
      : {
          ...employeeEntity,
          hireDate: convertDateTimeFromServer(employeeEntity.hireDate),
          zoneDF: convertDateTimeFromServer(employeeEntity.zoneDF),
          manager: employeeEntity?.manager?.id,
          department: employeeEntity?.department?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.employee.home.createOrEditLabel" data-cy="EmployeeCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.employee.home.createOrEditLabel">Create or edit a Employee</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="employee-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.firstName')}
                id="employee-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
              />
              <UncontrolledTooltip target="firstNameLabel">
                <Translate contentKey="jhipsterSampleApplicationApp.employee.help.firstName" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.lastName')}
                id="employee-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.email')}
                id="employee-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.phoneNumber')}
                id="employee-phoneNumber"
                name="phoneNumber"
                data-cy="phoneNumber"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.hireDate')}
                id="employee-hireDate"
                name="hireDate"
                data-cy="hireDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.salary')}
                id="employee-salary"
                name="salary"
                data-cy="salary"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.commissionPct')}
                id="employee-commissionPct"
                name="commissionPct"
                data-cy="commissionPct"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.longF')}
                id="employee-longF"
                name="longF"
                data-cy="longF"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.intF')}
                id="employee-intF"
                name="intF"
                data-cy="intF"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.boolF')}
                id="employee-boolF"
                name="boolF"
                data-cy="boolF"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.bigF')}
                id="employee-bigF"
                name="bigF"
                data-cy="bigF"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.durF')}
                id="employee-durF"
                name="durF"
                data-cy="durF"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.localDF')}
                id="employee-localDF"
                name="localDF"
                data-cy="localDF"
                type="date"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.zoneDF')}
                id="employee-zoneDF"
                name="zoneDF"
                data-cy="zoneDF"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.uuF')}
                id="employee-uuF"
                name="uuF"
                data-cy="uuF"
                type="text"
              />
              <ValidatedBlobField
                label={translate('jhipsterSampleApplicationApp.employee.pict')}
                id="employee-pict"
                name="pict"
                data-cy="pict"
                isImage
                accept="image/*"
                validate={{}}
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.employee.comments')}
                id="employee-comments"
                name="comments"
                data-cy="comments"
                type="textarea"
              />
              <ValidatedBlobField
                label={translate('jhipsterSampleApplicationApp.employee.cv')}
                id="employee-cv"
                name="cv"
                data-cy="cv"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                id="employee-manager"
                name="manager"
                data-cy="manager"
                label={translate('jhipsterSampleApplicationApp.employee.manager')}
                type="select"
              >
                <option value="" key="0" />
                {employees
                  ? employees.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-department"
                name="department"
                data-cy="department"
                label={translate('jhipsterSampleApplicationApp.employee.department')}
                type="select"
              >
                <option value="" key="0" />
                {departments
                  ? departments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EmployeeUpdate;
