CREATE OR REPLACE FUNCTION ws_tree_children(parent_id integer) RETURNS text AS
$BODY$

BEGIN

RETURN 'SELECT
        -1 AS id, 
        w.name AS name, 
        (SELECT id FROM cs_class WHERE table_name ILIKE ''WORLDSTATES'') AS class_id, 
        w.id AS object_id, 
        ''O'' AS node_type, 
        null AS url, 
        (SELECT ws_tree_children(w.id)) AS dynamic_children, 
        false AS sql_sort,
        true AS derive_permissions_from_class,
        (SELECT id FROM cs_class WHERE table_name ILIKE ''WORLDSTATES'') || ''@'' || w.id AS artificial_id

        FROM WORLDSTATES w WHERE w.parentWorldstate = ' || parent_id;

END;        

$BODY$

LANGUAGE 'plpgsql' IMMUTABLE