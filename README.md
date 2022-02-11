# TP-XML

Este repositorio consta de una aplicación java, la cual utiliza Maven,  capaz de validar archivos XML a partir del correspondiente XSD.

## Parseo de XML

Además de validar el contenido del archivo la aplicación permite trabajar sobre un contenido del mismo.
Para esto se trabajó sobre un XSD que representa la formación y resultado de un partido de futbol.

Cuyo formato puede encontrarse en [`resources`](./src/main/resources/xml-schema.xsd).

## Prerrequisitos

Para poder ejecutar la aplicación es necesario instalar java 11 y Apache Maven 3.8.1.

## Instrucciones para probar

Una vez preparado el ambiente puede probarse de manera local. Al ejecutar el programa este buscará dentro de resources el archivo llamado procesar.xml, este será validado y luego se visualizará un menú con acciones a realizar con el mismo.


``` Validando XML con XSD 
archivo correcto

MENU
1 Mostrar Formaciones
2 Mostrar FiguraPartido
3 Mostrar Resultado
4 Exportar XML
5 Salir
Ingrese una opcion:

``` 
Al seleccionar la opción ``4 Exportar XML`` se solicitara que ingrese una nota y el archivo generado podrá encontrarse en la raíz del proyecto.

## Consigna 

Esta aplicación surge de un trabajo práctico de la materia Programación Distribuida 2 de la UNDAV, dictada por el docente  Juan Lagostena, cuya consigna puede encontrarse en este [link](https://docs.google.com/document/d/1jOSzAYlrHEf3Y6eUqshWvW5Plm5pal92bfR612CyHp0).