import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { DurationFormat } from 'app/shared/DurationFormat';

import { getEntities } from './employee.reducer';

export const Employee = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const employeeList = useAppSelector(state => state.employee.entities);
  const loading = useAppSelector(state => state.employee.loading);
  const totalItems = useAppSelector(state => state.employee.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="employee-heading" data-cy="EmployeeHeading">
        <Translate contentKey="jhipsterSampleApplicationApp.employee.home.title">Employees</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterSampleApplicationApp.employee.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/employee/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterSampleApplicationApp.employee.home.createLabel">Create new Employee</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeeList && employeeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.firstName">First Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('firstName')} />
                </th>
                <th className="hand" onClick={sort('lastName')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.lastName">Last Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastName')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.email">Email</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('phoneNumber')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.phoneNumber">Phone Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('phoneNumber')} />
                </th>
                <th className="hand" onClick={sort('hireDate')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.hireDate">Hire Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('hireDate')} />
                </th>
                <th className="hand" onClick={sort('salary')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.salary">Salary</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('salary')} />
                </th>
                <th className="hand" onClick={sort('commissionPct')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.commissionPct">Commission Pct</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('commissionPct')} />
                </th>
                <th className="hand" onClick={sort('longF')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.longF">Long F</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('longF')} />
                </th>
                <th className="hand" onClick={sort('intF')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.intF">Int F</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('intF')} />
                </th>
                <th className="hand" onClick={sort('boolF')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.boolF">Bool F</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('boolF')} />
                </th>
                <th className="hand" onClick={sort('bigF')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.bigF">Big F</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bigF')} />
                </th>
                <th className="hand" onClick={sort('durF')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.durF">Dur F</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('durF')} />
                </th>
                <th className="hand" onClick={sort('localDF')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.localDF">Local DF</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('localDF')} />
                </th>
                <th className="hand" onClick={sort('zoneDF')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.zoneDF">Zone DF</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('zoneDF')} />
                </th>
                <th className="hand" onClick={sort('uuF')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.uuF">Uu F</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('uuF')} />
                </th>
                <th className="hand" onClick={sort('pict')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.pict">Pict</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('pict')} />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.comments">Comments</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('comments')} />
                </th>
                <th className="hand" onClick={sort('cv')}>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.cv">Cv</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cv')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.manager">Manager</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleApplicationApp.employee.department">Department</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeList.map((employee, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/employee/${employee.id}`} color="link" size="sm">
                      {employee.id}
                    </Button>
                  </td>
                  <td>{employee.firstName}</td>
                  <td>{employee.lastName}</td>
                  <td>{employee.email}</td>
                  <td>{employee.phoneNumber}</td>
                  <td>{employee.hireDate ? <TextFormat type="date" value={employee.hireDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{employee.salary}</td>
                  <td>{employee.commissionPct}</td>
                  <td>{employee.longF}</td>
                  <td>{employee.intF}</td>
                  <td>{employee.boolF ? 'true' : 'false'}</td>
                  <td>{employee.bigF}</td>
                  <td>{employee.durF ? <DurationFormat value={employee.durF} /> : null}</td>
                  <td>{employee.localDF ? <TextFormat type="date" value={employee.localDF} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{employee.zoneDF ? <TextFormat type="date" value={employee.zoneDF} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{employee.uuF}</td>
                  <td>
                    {employee.pict ? (
                      <div>
                        {employee.pictContentType ? (
                          <a onClick={openFile(employee.pictContentType, employee.pict)}>
                            <img src={`data:${employee.pictContentType};base64,${employee.pict}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employee.pictContentType}, {byteSize(employee.pict)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{employee.comments}</td>
                  <td>
                    {employee.cv ? (
                      <div>
                        {employee.cvContentType ? (
                          <a onClick={openFile(employee.cvContentType, employee.cv)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {employee.cvContentType}, {byteSize(employee.cv)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{employee.manager ? <Link to={`/employee/${employee.manager.id}`}>{employee.manager.id}</Link> : ''}</td>
                  <td>{employee.department ? <Link to={`/department/${employee.department.id}`}>{employee.department.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/employee/${employee.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee/${employee.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/employee/${employee.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterSampleApplicationApp.employee.home.notFound">No Employees found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={employeeList && employeeList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Employee;
