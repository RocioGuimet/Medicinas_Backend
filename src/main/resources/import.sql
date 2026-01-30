-- LIMPIAR SI EXISTE
DELETE FROM medicina_sintoma;
DELETE FROM medicinas;
DELETE FROM sintomas;

-- Insertar síntomas
INSERT INTO sintomas (id, nombre, descripcion) VALUES
(1, 'Dolor de cabeza', 'Malestar en la región craneal'),
(2, 'Insomnio', 'Dificultad para conciliar o mantener el sueño');

-- Insertar medicinas
INSERT INTO medicinas (id, nombre, descripcion, modo_uso) VALUES
(1, 'Manzanilla', 'Calmante natural suave', 'Infusión de 5-10 minutos'),
(2, 'Jengibre', 'Raíz antiinflamatoria', 'Infusión o rallado en comidas');