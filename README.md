# Logística: centro de distribución y camión

Programa de consola en Java que simula el **ingreso de paquetes al centro de distribución** (con reglas de prioridad) y la **carga en un camión** (orden LIFO: último en subir, primero en bajar).

---

## Estructura del código

Los fuentes viven bajo `src/structure/`. La convención del TP es **`definition`** para los **TDA** (interfaces / contratos) e **`implementation`** para las **TF** (tipos finitos / implementaciones concretas).

```
src/structure/
├── Main.java                    ← entrada: menú de consola
├── definition/                  ← interfaces (qué debe cumplir cada TDA)
│   ├── ContenidoPaqueteADT.java
│   ├── PaqueteADT.java
│   ├── CamionADT.java
│   └── CentroDistribucionADT.java
└── implementation/              ← clases que implementan esos contratos
    ├── ElectronicaTF.java
    ├── AlimentosTF.java
    ├── FragilesTF.java
    ├── PaqueteTF.java
    ├── CamionTF.java
    └── CentroDistribucionTF.java
```

### Carpeta `definition` (interfaces)

| Archivo | Rol |
|--------|-----|
| **ContenidoPaqueteADT** | Marca común (“tipo de carga”) para lo que va **dentro** de un paquete. No define métodos: sirve para tipar genéricos y agrupar electrónica, alimentos y frágiles. |
| **`PaqueteADT<T>`** | Contrato de un **paquete**: ID, peso, destino, contenido, si es urgente y si tiene **prioridad en el centro** (urgente o peso por encima del umbral definido en la implementación). |
| **`CamionADT<T>`** | Contrato del **camión como pila (LIFO)**: cargar, descargar tope, deshacer última carga, consultas de cantidad / vacío / ver próximo sin sacar. |
| **`CentroDistribucionADT<T>`** | Contrato del **centro**: ingresar paquetes, retirar el siguiente a procesar (según prioridad + orden de llegada en la TF), ver siguiente sin sacar y cantidad pendiente. |

### Carpeta `implementation` (clases concretas)

| Archivo | Rol |
|--------|-----|
| **ElectronicaTF** | Contenido tipo electrónica: descripción y valor asegurado. Implementa **ContenidoPaqueteADT**. |
| **AlimentosTF** | Contenido tipo alimentos: nombre del producto y si requiere frío. Implementa **ContenidoPaqueteADT**. |
| **FragilesTF** | Contenido tipo frágiles: descripción y nivel de cuidado (entero). Implementa **ContenidoPaqueteADT**. |
| **`PaqueteTF<T>`** | Implementación de **PaqueteADT**: valida datos, asigna ID autonumérico y calcula prioridad de centro según urgencia y peso. |
| **`CamionTF<T>`** | Implementación de **CamionADT** con estructura de **pila** (último cargado = primero en descargar). |
| **`CentroDistribucionTF<T>`** | Implementación de **CentroDistribucionADT** con cola que respeta **prioridad** y **FIFO** entre iguales. |

### `Main.java`

No es TDA ni TF: crea el centro y el camión, arma el bucle del **menú** y lee por teclado para delegar en las operaciones de las TF.

---

## Cómo ejecutar

1. Abrí el proyecto en tu IDE.
2. Marcá la carpeta **`src`** como carpeta raíz de código fuente si el IDE no la detecta sola.
3. Abrí **`structure.Main`** y **ejecutá / Run / Play** sobre esa clase (o “Run Main”). Con eso alcanza: se abre la consola y aparece el menú.

**Requisito:** Java **14 o superior** (se usa `switch` con `->`).

Si preferís la terminal, desde la raíz del proyecto podés compilar y lanzar así:

```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out structure.Main
```

---

## Menú principal

Tras el título, verás `Opción:`. Ingresá **un número** y pulsá Enter.

| Opción | Nombre | Qué hace |
|--------|--------|----------|
| **1** | Ingresar paquete al centro | Carga datos de un paquete nuevo y lo mete en la cola del centro. |
| **2** | Procesar siguiente del centro | Saca de la cola el próximo paquete según **prioridad** y orden de llegada; queda “listo” para el camión. |
| **3** | Cargar al camión | Sube al camión el paquete que quedó listo con la opción **2** (si no hay, avisa). |
| **4** | Descargar del camión | Baja del camión el paquete del **tope** (el último que cargaste). |
| **5** | Deshacer última carga | Quita del camión la **última** carga (por si hubo error de destino). |
| **6** | Ver estado | Muestra resumen: cola del centro, camión y si hay paquete listo para cargar. |
| **0** | Salir | Termina el programa (muestra `Fin.`). |

Si escribís algo que no es un número entero en `Opción:`, verás: *Ingresá un número de opción.*

---

## Opción 1 — Ingresar paquete al centro

El programa pregunta en este orden:

1. **Peso (kg):** número decimal o entero, **mayor que 0** (ej. `12` o `2.5`). Usá **punto** para decimales (ej. `10.5`).
2. **Destino:** texto libre (ciudad, sucursal, etc.), no puede quedar vacío.
3. **¿Urgente? (s/n):** respondé **s** (sí) o **n** (no). También valen `si`, `sí`, `y`, `yes` como afirmación (sin importar mayúsculas).
4. **Tipo de contenido del paquete** — elegí **1**, **2** o **3**:

   ### 1) Electrónica
   - **Descripción:** texto (ej. `Notebook`).
   - **Valor asegurado:** número (ej. `1500`).

   ### 2) Alimentos
   - **Nombre del producto:** texto (ej. `Yogur`).
   - **¿Requiere frío? (s/n):** igual que urgente (`s` / `n`).

   ### 3) Frágiles
   - **Descripción:** texto (ej. `Vidrio`).
   - **Nivel de cuidado (entero):** un **entero** que vos definas como escala (ej. `1` poco cuidado, `10` mucho). Solo números enteros.

Si algo falla (peso ≤ 0, destino vacío, tipo distinto de 1–3, número inválido), verás *Error: …* y el paquete **no** se ingresa.

**Prioridad en el centro:** se atienden antes los paquetes **marcados urgentes** o con **peso mayor a 50 kg**; el resto va después. Entre paquetes del mismo nivel se respeta el **orden de llegada** (FIFO).

---

## Opción 2 — Procesar siguiente

- Si **no** había un paquete “esperando” cargar al camión: saca el siguiente de la **cola del centro** y lo muestra como *Procesado: …*. Ese paquete queda **listo para el camión**.
- Si **ya** había uno procesado y aún no lo cargaste al camión con la opción **3**, no lo reemplaza: te avisa que primero uses **3** o dejes el flujo como está.

Si no hay nadie en cola: *No hay paquetes pendientes en el centro.*

---

## Opción 3 — Cargar al camión

Solo funciona si antes usaste la opción **2** y todavía no cargaste ese paquete. Sube el paquete al **tope** de la pila del camión.

Si no hay nada listo: *No hay paquete procesado listo. Usá la opción 2 primero.*

---

## Opciones 4 y 5 — Camión (LIFO)

- **4 Descargar:** baja el paquete que está **arriba** (el último que cargaste). Si el camión está vacío, lo indica.
- **5 Deshacer última carga:** quita el mismo paquete del tope (útil si equivocaste el destino al cargar). Si está vacío, avisa.

En ambos casos el orden es **último en entrar, primero en salir**.

---

## Opción 6 — Ver estado

Muestra:

- Cuántos paquetes faltan en el **centro** y cuál sería el **próximo** sin sacarlo.
- Cuántos hay en el **camión** y cuál está en el **tope** (el que saldría al descargar).
- Si hay un paquete **listo para cargar** (resultado de la opción **2** que aún no pasó por la **3**).

---

## Flujo típico de ejemplo

1. **1** — Ingresar uno o más paquetes al centro.  
2. **2** — Procesar el siguiente (sale según prioridad + orden de llegada).  
3. **3** — Cargar ese paquete al camión.  
4. Repetir **2** y **3** si querés más cargas.  
5. **4** o **5** para bajar o corregir la última carga del camión.  
6. **6** cuando quieras ver el resumen.  
7. **0** para salir.

Los datos viven **solo en memoria**: al cerrar el programa no se guardan.
