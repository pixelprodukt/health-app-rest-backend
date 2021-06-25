DROP TABLE IF EXISTS medication_plans;
DROP TABLE IF EXISTS medication_units;
DROP TABLE IF EXISTS intake_types;
DROP TABLE IF EXISTS allergies;
DROP TABLE IF EXISTS blood_pressures;
DROP TABLE IF EXISTS users;

create table users (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    email varchar(200) not null unique,
    password varchar(200) not null,
    name varchar(200) unique,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    PRIMARY KEY (id)
);

create table blood_pressures (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT,
    systolic_value SMALLINT NOT NULL,
    diastolic_value SMALLINT NOT NULL,
    pulse_rate SMALLINT NOT NULL,
    measured_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    arm_side VARCHAR(1) CHECK (arm_side in ('L', 'R')),
    comment VARCHAR(600),
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

create table allergies (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT,
    description varchar(100) not null,
    reaction varchar(600),
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    PRIMARY KEY (id),
    constraint fk_user_id foreign key (user_id) references users(id) ON DELETE CASCADE
);

create table intake_types (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name varchar(40) NOT NULL,
    PRIMARY KEY (id)
);

create table medication_units (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name varchar(40) NOT NULL,
    PRIMARY KEY (id)
);

create table medication_plans (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT,
    emergency_medication BOOLEAN,
    medication_until timestamp without time zone,
    substance varchar(100),
    brand_name varchar(100),
    dosage varchar(20),
    intake_type_id BIGINT,
    medication_unit_id BIGINT,
    morning_intake smallint,
    noon_intake smallint,
    evening_intake smallint,
    night_intake smallint,
    comment varchar(600),
    reason varchar(100),
    PRIMARY KEY (id),
    constraint fk_user_id foreign key (user_id) references users(id) ON DELETE CASCADE,
    CONSTRAINT fk_intake_type_id FOREIGN KEY (intake_type_id) REFERENCES intake_types(id) ON DELETE CASCADE
);
