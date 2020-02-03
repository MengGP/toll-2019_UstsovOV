Учебный проект по курсу "Профессиональная разработка на языке Java"
==================================================================
курс повышения квалификации ТУСУР - "Профессиональная разработка на языке Java" 
студент: Усцов Олег
project-name: toll-2019_UstsoOV

Описание проекта:
====================
Проект состоит из нескольких функциональных модулей:

- Модуль трекера (tracker-core): эмулирует работу GPS-трекера, в качестве иточника данных выступает файл 
с GPS-треком *.kml, разобранные данные из файла передаются в модуль сервера (server-core) в виде POST-запросов. 
Также в данном модуле реализовано сохранение в разобранных GPS-данных "локальную" (для данного метода) 
базу банных - БД - H2-файловый, работа с БД - JDBC.

- Модуль сервера (server-core): эмулирует рабу сервера распределнной системы, реализует работу с БД - 
данные GPS и данные пользователей системы. Данные GPS модуль сервера получает в виде POST-запросов от 
модуля трекера, данные БД пользователей редактирутся из модуль интрефейса-сервара (server-ui) - 
взаимодействие по HTTP. Также реализован фунционал предоставления данных (данные GPS, пользователи) 
по запросу от модуля интрефеса-сервера.

 - Модуль интерфеса сервера (server-ui): реализует интерфейс управления модулем сервера (server-ui). 
 Релизован фунционал работы с базой пользователей хранящейся с БД модуля севера и функционал запроса
 GPS-данных БД из модуля сервера.        

Краткое поисание программных модулей
=====================================
 
 - server-core:     модуль эмуляции сервера - реализует работу с БД, взаимодействует с модулями "tracker-core" и "server-ui" 
 - server-ui:       реаизует интерфейс упраляния данными
 - tracker-core:    медуль эмулирующий работу GPS-трекера
 - common:          вспомогательный модуль, общий для всех проектов

Система сборки: gradle 3.1


Иструкция по запуску (из среды разработки IntelliJ IDEA):
---------------------------------------------------------

1. Запускаем модуль "tracker-core":
	- для старта запускаем класс > menggp.tracker.springApp.Application
	- модуль запускается в виде SpringBootApplication, но без "web-сервера", для того, чтобы запустить остальные модули на одном хосте
	- после старта модуля выполняются следующие действия:
		- парсинг файла *.kml: получаем координаты, рассчитываем азимут, время, скорость(случайным образом), добавляем номер авто
		- пишем информацию в БД h2 - файловый вариант (БД очищается перед началом записи)
		- параллельно данные склвдываются в очередь в формате JSON для дальнейшей отправки в модуль "server-core"
			с определенной периодичностью, по мере постпления
		- данные отправляются в модуль "server-core" POST-запросом - по одной записи из очереди
			с определенной периодичностью, по мере постпления в очередь

2. Запускаем модуль "server-core"

3. Запускаем модуль "server-ui" 
			 
			

Подробное описание программных модулей:
=====================================

server-core
-----------

server-ui
----------

tracker-core
------------
Используемые технологии:
   - Spring Framework
   - Spring Boot
   - Spring Data JPA
   - Java API for KML

common
--------
Вспомогательный модуль - для организации обмена между модулями.



---------------------
---------------------
 *** описание проекта в процессе доработки
