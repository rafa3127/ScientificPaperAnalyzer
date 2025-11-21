# DESARROLLO.md

## Descripción del Proyecto

Sistema para administrar y analizar resúmenes de artículos científicos. Permite cargar, buscar y analizar investigaciones usando estructuras de datos eficientes.

**Nombre:** ScientificPaperAnalyzer  
**Curso:** Estructuras de Datos - BPTSP06  
**Universidad:** Universidad Metropolitana

---

## Tecnologías

- **Lenguaje:** Java 8+
- **IDE:** NetBeans
- **GUI:** Swing
- **Control de versiones:** Git + GitHub
- **Estructuras de datos:** HashTable y AVL (implementación propia)
- **Librerías permitidas:**
  - `java.text.Collator` - Para comparar palabras con acentos
  - `java.util.Locale` - Para configuración en español
  - `javax.swing.*` - Para la interfaz gráfica

---

## Plan de Desarrollo

### Fase 1: Estructuras de Datos Base
- [x] HashTable genérica con función hash personalizada
- [x] AVLTree genérica con rotaciones
- [x] LinkedList para manejar colisiones

### Fase 2: Modelos + Persistencia
- [ ] Clase Resumen
- [ ] Parser para leer archivos .txt
- [ ] FileManager para guardar/cargar datos
- [ ] Archivos de ejemplo

### Fase 3: Repositorios
- [ ] ResumenRepository (usa HashTable)
- [ ] PalabraClaveRepository (usa AVL + HashTable inversa)
- [ ] AutorRepository (usa AVL)

### Fase 4: Servicios
- [ ] ResumenService (agregar, validar)
- [ ] BusquedaService (buscar por palabra/autor)
- [ ] AnalisisService (contar frecuencias)

### Fase 5: UI
- [ ] MainFrame
- [ ] Paneles para cada funcionalidad
- [ ] JFileChooser para cargar archivos
- [ ] Dialogs para mostrar detalles

### Fase 6: Documentación
- [ ] Javadoc completo
- [ ] Diagrama UML
- [ ] README.md
- [ ] Precarga de datos
- [ ] Tests de integración

---
