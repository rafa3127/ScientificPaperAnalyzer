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
- [x] Clase Resumen
- [x] Parser para leer archivos .txt
- [x] FileManager para guardar/cargar datos
- [x] Archivos de ejemplo

### Fase 3: Repositorios
- [x] hashmap para Resumenes
- [x] AVL para Autores
- [x] AVL para palabras claves

### Fase 4: Servicios
- [x] Servicio de resumenes
- [x] Servicio de Keywords
- [x] Servicio de busqueda
- [x] Servicio de análisis de resumenes

### Fase 5: UI
- [x] MainFrame
- [x] Panel de Agregar Resumen
- [x] Panel de Analizar Resumen
- [x] Panel de Buscar Keyword
- [x] Panel de Buscar Autor
- [x] Panel de Listar Keyword

### Fase 6: Documentación
- [x] Javadoc completo
- [x] Diagrama UML
- [ ] README.md

---
