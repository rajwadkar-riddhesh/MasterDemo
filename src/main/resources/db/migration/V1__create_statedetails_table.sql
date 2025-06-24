CREATE TABLE statedetailsflyway (
    state_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    state_name VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    status VARCHAR(50)
);
