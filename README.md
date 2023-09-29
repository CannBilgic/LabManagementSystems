# Laboratuvar Yönetim Sistemi

#### Proje Özellikleri

* Rapor Tanımı ( Dosya numarası, Hasta Ad ve Soyad, Hasta Kimlik Numarası(TC), Koyulan Tanı Başlığı, Tanı Detayları, Raporun Verildiği Tarih, Fiziksel Rapora Ait .png/.jpg Formatında Bir Adet Fotoğraf )
* Bir rapor yalnızca bir laborant tarafından tanımlanabilir. Bir laborant ise n tane rapor tanımlayabilir. ( Ad, Soyad, Rol Yetkisi, Email , Kullanıcı Adı ,Hastane Kimlik Numarası(7 Haneli), )
* Hasta adı/soyadı, Hasta kimlik numarası, Laborant adı/soyadı bilgileri ile arama yapabilir. Rapor tarihi ile sıralama yapabilir
* Var olan bir rapor üzerinde değişiklik yapılabilir
* Var olan tüm raporların detayları incelenebilir
* Var olan bir rapor silinebilir
* Kullanıcılar sisteme kullanıcı adı/parola ile giriş yapabilir
* Bir yetkilendirme mekanizması içeriyor. Yönetici tüm eylemleri gerçekleştirebilir. Kullanıcı kayıt oluşturabilir ve raporlarını görüntüleyebilir.
* Custom error page handling yapıldı
* İmagelar byte olarak PostgreSQLde tutuldu.

**Bağımlılıklar:** Java 19, Maven, PostgreSQL 15

### Veritabanı Oluşturma
```sh
$ createdb -h localhost -p 5432 -U postgres OzgurYazilim
```

### Varsayılan Veritabanı Ayarları
Bu ayarları **application.yaml** dosyasından değiştirebilirsiniz. Bu özellikleri düzenlemezseniz **Bağımlılıkları yükleme** adımında bir **hata** ile karşılaşabilirsiniz.

`datasource:`

`url: "jdbc:postgresql://localhost:5432/OzgurYazilim"`

`username: postgres`

`password: 12345`

### Bağımlılıkları yükleme

```sh
$ mvn clean install
```

### Başlatma

```sh
$ mvn spring-boot:run
```
veya

```sh
$ java -jar target/OzgurYazilimProje-0.0.1-SNAPSHOT.jar
```

### Giriş bilgileri

* Web sayfası kullanıcılara açıktır. Web sayfası açıldıktan sonra bir LABORANT kullanıcısı veya PATIENT kullanıcısı oluşturulabilir.

* Login işlemi kullanıcı adı ve password ile gerçekleşir.

* USER kendi eklediği raporları görüntüleyebilir.


### Notlar

* Hasta silindiği zaman ona ait görselde beraberinde silinir.
