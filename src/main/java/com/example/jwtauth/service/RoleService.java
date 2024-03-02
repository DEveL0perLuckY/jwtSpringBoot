package com.example.jwtauth.service;

import com.example.jwtauth.domain.Role;
import com.example.jwtauth.model.RoleDTO;
import com.example.jwtauth.repos.RoleRepository;
import com.example.jwtauth.repos.UserRepository;
import com.example.jwtauth.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@SuppressWarnings("null")

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(final RoleRepository roleRepository, final UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("id"));
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO get(final Long id) {
        return roleRepository.findById(id)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getId();
    }

    public void update(final Long id, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Long id) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        userRepository.findAllByRoleId(role)
                .forEach(user -> user.getRoleId().remove(role));
        roleRepository.delete(role);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setName(roleDTO.getName());
        return role;
    }

}
