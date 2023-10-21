import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { DurationFormat } from 'app/shared/DurationFormat';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee.reducer';

export const EmployeeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeEntity = useAppSelector(state => state.employee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeDetailsHeading">
          <Translate contentKey="jhipsterSampleApplicationApp.employee.detail.title">Employee</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.firstName">First Name</Translate>
            </span>
            <UncontrolledTooltip target="firstName">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.help.firstName" />
            </UncontrolledTooltip>
          </dt>
          <dd>{employeeEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.phoneNumber}</dd>
          <dt>
            <span id="hireDate">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.hireDate">Hire Date</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.hireDate ? <TextFormat value={employeeEntity.hireDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="salary">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.salary">Salary</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.salary}</dd>
          <dt>
            <span id="commissionPct">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.commissionPct">Commission Pct</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.commissionPct}</dd>
          <dt>
            <span id="longF">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.longF">Long F</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.longF}</dd>
          <dt>
            <span id="intF">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.intF">Int F</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.intF}</dd>
          <dt>
            <span id="boolF">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.boolF">Bool F</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.boolF ? 'true' : 'false'}</dd>
          <dt>
            <span id="bigF">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.bigF">Big F</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.bigF}</dd>
          <dt>
            <span id="durF">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.durF">Dur F</Translate>
            </span>
          </dt>
          <dd>
            {employeeEntity.durF ? <DurationFormat value={employeeEntity.durF} /> : null} ({employeeEntity.durF})
          </dd>
          <dt>
            <span id="localDF">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.localDF">Local DF</Translate>
            </span>
          </dt>
          <dd>
            {employeeEntity.localDF ? <TextFormat value={employeeEntity.localDF} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="zoneDF">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.zoneDF">Zone DF</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.zoneDF ? <TextFormat value={employeeEntity.zoneDF} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="uuF">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.uuF">Uu F</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.uuF}</dd>
          <dt>
            <span id="pict">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.pict">Pict</Translate>
            </span>
          </dt>
          <dd>
            {employeeEntity.pict ? (
              <div>
                {employeeEntity.pictContentType ? (
                  <a onClick={openFile(employeeEntity.pictContentType, employeeEntity.pict)}>
                    <img src={`data:${employeeEntity.pictContentType};base64,${employeeEntity.pict}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {employeeEntity.pictContentType}, {byteSize(employeeEntity.pict)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.comments}</dd>
          <dt>
            <span id="cv">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.cv">Cv</Translate>
            </span>
          </dt>
          <dd>
            {employeeEntity.cv ? (
              <div>
                {employeeEntity.cvContentType ? (
                  <a onClick={openFile(employeeEntity.cvContentType, employeeEntity.cv)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeEntity.cvContentType}, {byteSize(employeeEntity.cv)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.employee.manager">Manager</Translate>
          </dt>
          <dd>{employeeEntity.manager ? employeeEntity.manager.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleApplicationApp.employee.department">Department</Translate>
          </dt>
          <dd>{employeeEntity.department ? employeeEntity.department.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeDetail;
