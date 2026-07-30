create table vehicles (
    id uuid primary key,
    registration_number varchar(32) not null unique,
    model varchar(120) not null,
    owner_name varchar(120) not null
);

create table service_requests (
    id uuid primary key,
    vehicle_id uuid not null references vehicles(id),
    description varchar(1000) not null,
    priority varchar(20) not null,
    status varchar(30) not null,
    assigned_technician varchar(120),
    created_at timestamptz not null,
    completed_at timestamptz
);

create index idx_service_requests_status on service_requests(status);
create index idx_service_requests_priority on service_requests(priority);
create index idx_service_requests_vehicle_id on service_requests(vehicle_id);
