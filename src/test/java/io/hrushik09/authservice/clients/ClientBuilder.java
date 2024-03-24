package io.hrushik09.authservice.clients;

public class ClientBuilder {
    private String pid = "somePid";
    private String clientId = "someClientId";

    private ClientBuilder() {
    }

    private ClientBuilder(ClientBuilder copy) {
        this.pid = copy.pid;
        this.clientId = copy.clientId;
    }

    public static ClientBuilder aClient() {
        return new ClientBuilder();
    }

    public ClientBuilder but() {
        return new ClientBuilder(this);
    }

    public Client build() {
        Client client = new Client();
        client.setPid(pid);
        client.setClientId(clientId);
        return client;
    }

    public ClientBuilder withPid(String pid) {
        this.pid = pid;
        return this;
    }

    public ClientBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }
}
