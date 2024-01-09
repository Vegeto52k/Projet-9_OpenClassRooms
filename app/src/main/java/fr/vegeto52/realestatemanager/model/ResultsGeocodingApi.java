package fr.vegeto52.realestatemanager.model;

import java.util.List;

@SuppressWarnings("unused")
public class ResultsGeocodingApi {


    /**
     * results : [{"address_components":[{"long_name":"Washington","short_name":"WA","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]}],"formatted_address":"Washington, USA","geometry":{"bounds":{"northeast":{"lat":49.0024442,"lng":-116.91558},"southwest":{"lat":45.543541,"lng":-124.8489739}},"location":{"lat":47.7510741,"lng":-120.7401385},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":49.0024442,"lng":-116.91558},"southwest":{"lat":45.543541,"lng":-124.8489739}}},"place_id":"ChIJ-bDD5__lhVQRuvNfbGh4QpQ","types":["administrative_area_level_1","political"]}]
     * status : OK
     */

    private String status;
    private List<Results> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public static class Results {
        /**
         * address_components : [{"long_name":"Washington","short_name":"WA","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]}]
         * formatted_address : Washington, USA
         * geometry : {"bounds":{"northeast":{"lat":49.0024442,"lng":-116.91558},"southwest":{"lat":45.543541,"lng":-124.8489739}},"location":{"lat":47.7510741,"lng":-120.7401385},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":49.0024442,"lng":-116.91558},"southwest":{"lat":45.543541,"lng":-124.8489739}}}
         * place_id : ChIJ-bDD5__lhVQRuvNfbGh4QpQ
         * types : ["administrative_area_level_1","political"]
         */

        private String formatted_address;
        private Geometry geometry;
        private String place_id;
        private List<AddressComponents> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<AddressComponents> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponents> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class Geometry {
            /**
             * bounds : {"northeast":{"lat":49.0024442,"lng":-116.91558},"southwest":{"lat":45.543541,"lng":-124.8489739}}
             * location : {"lat":47.7510741,"lng":-120.7401385}
             * location_type : APPROXIMATE
             * viewport : {"northeast":{"lat":49.0024442,"lng":-116.91558},"southwest":{"lat":45.543541,"lng":-124.8489739}}
             */

            private Bounds bounds;
            private Location location;
            private String location_type;
            private Viewport viewport;

            public Bounds getBounds() {
                return bounds;
            }

            public void setBounds(Bounds bounds) {
                this.bounds = bounds;
            }

            public Location getLocation() {
                return location;
            }

            public void setLocation(Location location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public Viewport getViewport() {
                return viewport;
            }

            public void setViewport(Viewport viewport) {
                this.viewport = viewport;
            }

            public static class Bounds {
                /**
                 * northeast : {"lat":49.0024442,"lng":-116.91558}
                 * southwest : {"lat":45.543541,"lng":-124.8489739}
                 */

                private Northeast northeast;
                private Southwest southwest;

                public Northeast getNortheast() {
                    return northeast;
                }

                public void setNortheast(Northeast northeast) {
                    this.northeast = northeast;
                }

                public Southwest getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(Southwest southwest) {
                    this.southwest = southwest;
                }

                public static class Northeast {
                    /**
                     * lat : 49.0024442
                     * lng : -116.91558
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class Southwest {
                    /**
                     * lat : 45.543541
                     * lng : -124.8489739
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class Location {
                /**
                 * lat : 47.7510741
                 * lng : -120.7401385
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class Viewport {
                /**
                 * northeast : {"lat":49.0024442,"lng":-116.91558}
                 * southwest : {"lat":45.543541,"lng":-124.8489739}
                 */

                private Northeast northeast;
                private Southwest southwest;

                public Northeast getNortheast() {
                    return northeast;
                }

                public void setNortheast(Northeast northeast) {
                    this.northeast = northeast;
                }

                public Southwest getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(Southwest southwest) {
                    this.southwest = southwest;
                }

                public static class Northeast {
                    /**
                     * lat : 49.0024442
                     * lng : -116.91558
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class Southwest {
                    /**
                     * lat : 45.543541
                     * lng : -124.8489739
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class AddressComponents {
            /**
             * long_name : Washington
             * short_name : WA
             * types : ["administrative_area_level_1","political"]
             */

            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}

