package nl.haploid.event.channel;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DockerService {

    @Value("${DOCKER_HOST_IP}")
    private String hostIp;

    @Autowired
    private DockerClient docker;

    public String getHostname() {
        return hostIp;
    }

    // TODO: or just start up dependencies on CLI; easy to do with fig, much less testing code

    public String startContainer(final String image, final String name, final Ports ports,
                                 final Binds binds, final Links links, final List<String> envs) {
        final CreateContainerCmd createContainer = docker.createContainerCmd(image)
                .withName(name);
        for (final Map.Entry<ExposedPort, Ports.Binding[]> entry : ports.getBindings().entrySet()) {
            createContainer.withExposedPorts(entry.getKey());
        }
        // TODO: envs
        final CreateContainerResponse container = createContainer.exec();
        final String id = container.getId();
        final StartContainerCmd startContainer = docker.startContainerCmd(id);
        for (Bind bind : binds.getBinds()) {
            startContainer.withBinds(bind);
        }
        // TODO: links
        startContainer
                .withPortBindings(ports)
                .exec();
        docker.waitContainerCmd(id);
        return id;
    }

    public void stopContainer(final Container container) {
        if (isRunning(container)) {
            docker.stopContainerCmd(container.getId()).exec();
        }
        docker.removeContainerCmd(container.getId()).exec();
    }

    public Container getContainerByName(final String containerName) {
        final List<Container> containers = docker
                .listContainersCmd()
                .withShowAll(true)
                .exec();
        for (final Container container : containers) {
            for (final String name : container.getNames()) {
                if (name.equals(String.format("/%s", containerName))) {
                    return container;
                }
            }
        }
        return null;
    }

    public boolean isRunning(final Container container) {
        return docker.inspectContainerCmd(container.getId()).exec().getState().isRunning();
    }
}
